/**
 ******************************************************************************
 * Copyright (c), Takenori Imoto
 * 楓 software http://www.kaede-software.com/
 * All rights reserved.
 ******************************************************************************
 * ソースコード形式かバイナリ形式か、変更するかしないかを問わず、以下の条件を満
 * たす場合に限り、再頒布および使用が許可されます。
 *
 * ・ソースコードを再頒布する場合、上記の著作権表示、本条件一覧、および下記免責
 *   条項を含めること。
 * ・バイナリ形式で再頒布する場合、頒布物に付属のドキュメント等の資料に、上記の
 *   著作権表示、本条件一覧、および下記免責条項を含めること。
 * ・書面による特別の許可なしに、本ソフトウェアから派生した製品の宣伝または販売
 *   促進に、組織の名前またはコントリビューターの名前を使用してはならない。
 *
 * 本ソフトウェアは、著作権者およびコントリビューターによって「現状のまま」提供
 * されており、明示黙示を問わず、商業的な使用可能性、および特定の目的に対する適
 * 合性に関する暗黙の保証も含め、またそれに限定されない、いかなる保証もありませ
 * ん。著作権者もコントリビューターも、事由のいかんを問わず、損害発生の原因いか
 * んを問わず、かつ責任の根拠が契約であるか厳格責任であるか（過失その他の）不法
 * 行為であるかを問わず、仮にそのような損害が発生する可能性を知らされていたとし
 * ても、本ソフトウェアの使用によって発生した（代替品または代用サービスの調達、
 * 使用の喪失、データの喪失、利益の喪失、業務の中断も含め、またそれに限定されな
 * い）直接損害、間接損害、偶発的な損害、特別損害、懲罰的損害、または結果損害に
 * ついて、一切責任を負わないものとします。
 ******************************************************************************
 * 本ソフトウェアは、吉里吉里2 ( http://kikyou.info/tvp/ ) のソースコードをJava
 * に書き換えたものを一部使用しています。
 * 吉里吉里2 Copyright (C) W.Dee <dee@kikyou.info> and contributors
 ******************************************************************************
 */
package jp.kirikiri.tvp2.base;

import java.util.HashMap;

import jp.kirikiri.tjs2.BinaryStream;
import jp.kirikiri.tjs2.TJSException;
import jp.kirikiri.tvp2.msg.Message;

public abstract class Archive {

	private HashMap<String, Integer> mHash;
	private boolean mInit;
	private String mArchiveName;

	//-- constructor
	public Archive( final String name ) {
		mArchiveName = name;
		//mInit = false;
		mHash = new HashMap<String, Integer>();
	}

	public abstract int getCount();

	/**
	 * @return name must be already normalized using NormalizeInArchiveStorageName
	 * and the index must be sorted by its name.
	 * this is needed by fast directory search.
	 */
	public abstract String getName( int idx);

	public abstract BinaryStream createStreamByIndex( int idx ) throws TJSException;


	public static String normalizeInArchiveStorageName( final String name ) {
		// normalization of in-archive storage name does :
		if( name == null || name.length() == 0 ) return null;

		// make all characters small
		// change '\\' to '/'
		String tmp = name.toLowerCase();
		tmp = tmp.replace('\\','/');

		// eliminate duplicated slashes
		char[] ptr = tmp.toCharArray();
		final int len = ptr.length;
		int dest = 0;
		for( int i = 0; i < len; ) {
			if( ptr[i] != '/' ) {
				ptr[dest] = ptr[i];
				i++;
				dest++;
			} else {
				if( i != 0 ) {
					ptr[dest] = ptr[i];
					i++;
					dest++;
				}
				while( i < len && ptr[i] == '/' ) i++;
			}
		}
		return new String( ptr, 0, dest );
	}

	private void addToHash() {
		// enter all names to the hash table
		final int count = getCount();
		for( int i = 0; i < count; i++ ) {
			String name = getName(i);
			name = normalizeInArchiveStorageName(name);
			mHash.put( name, i );
		}
	}
	public BinaryStream createStream( final String name ) throws TJSException {
		if( name == null || name.length() == 0 ) return null;

		if( !mInit ) {
			mInit = true;
			addToHash();
		}

		Integer p = mHash.get(name);
		if( p == null ) Message.throwExceptionMessage( Message.StorageInArchiveNotFound, name, mArchiveName );
		return createStreamByIndex(p);

	}
	public boolean isExistent( final String name ) {
		if( name == null || name.length() == 0 ) return false;
		if( !mInit ) {
			mInit = true;
			addToHash();
		}
		return mHash.get(name) != null;
	}

	/**
	 * the item must be sorted by operator < , otherwise this function
	 * will not work propertly.
	 * @return first index which have 'prefix' at start of the name.
	 * @return -1 if the target is not found.
	 */
	public int getFirstIndexStartsWith( final String prefix ) {
		int total_count = getCount();
		int s = 0, e = total_count;
		while( e - s > 1 ) {
			int m = (e + s) / 2;
			if( !(getName(m).compareTo(prefix) < 0) ) {
				// m is after or at the target
				e = m;
			} else {
				// m is before the target
				s = m;
			}
		}

		// at this point, s or s+1 should point the target.
		// be certain.
		if( s >= total_count) return -1; // out of the index
		if( getName(s).startsWith(prefix) ) return s;
		s++;
		if( s >= total_count ) return -1; // out of the index
		if( getName(s).startsWith(prefix) ) return s;
		return -1;
	}
}
