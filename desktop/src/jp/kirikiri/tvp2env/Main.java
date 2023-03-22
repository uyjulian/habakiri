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
package jp.kirikiri.tvp2env;

import java.util.Arrays;

import jp.kirikiri.tjs2.BinaryStream;
import jp.kirikiri.tjs2.CompileException;
import jp.kirikiri.tjs2.ConsoleOutput;
import jp.kirikiri.tjs2.Dispatch2;
import jp.kirikiri.tjs2.Error;
import jp.kirikiri.tjs2.TJS;
import jp.kirikiri.tjs2.TJSException;
import jp.kirikiri.tjs2.Variant;
import jp.kirikiri.tjs2.VariantException;

import jp.kirikiri.tvp2.base.Storage;
import jp.kirikiri.tvp2.base.StorageIO;

public class Main {
	static private TJS mScriptEngine;
	public static ConsoleOutput ConsoleOutputGetway;
	static private byte[] mHeaderTemp;

	public static void main(String[] args) throws Exception {
		mHeaderTemp = new byte[8];

		Storage.initialize();

		TJS.mStorage = new StorageIO();
		TJS.initialize();
		mScriptEngine = new TJS();

		ConsoleOutputGetway = new DefaultLogger();
		TJS.setConsoleOutput( ConsoleOutputGetway );

		executeStorage(args[0],null,null,false,null);
	}

	public static Dispatch2 getGlobal() {
		return mScriptEngine.getGlobal();
	}

	public static void executeStorage( final String name, Dispatch2 context, Variant result, boolean isexpression, final String modestr ) throws TJSException, VariantException, CompileException {
		if( mScriptEngine == null ) throw new TJSException( Error.InternalError );

		if( true ) {
			if( isexpression == false ) {
				// ヘッダーチェック
				String place = Storage.searchPlacedPath(name);
				String shortname = Storage.extractStorageName(place);
				BinaryStream stream = Storage.createStream( place, BinaryStream.READ );
				int len = stream.read(mHeaderTemp);
				if( len == 8 && Arrays.equals(mHeaderTemp, jp.kirikiri.tjs2.Compiler.FILE_TAG) ) {
					stream.setPosition(0);
					mScriptEngine.loadByteCode(result, context, shortname, stream );
					return;
				} else {
					// ヘッダーがバイナリのバイトコードでなくて読み替えもしないのならスクリプトとして読む
					stream.setPosition(0);
					String buffer = Storage.readText(stream,place,modestr);
					if( buffer == null ) buffer = "";
					mScriptEngine.execScript(buffer, result, context, shortname, 0 );
					return;
				}
			}
		}

		String place = Storage.searchPlacedPath(name);
		String shortname = Storage.extractStorageName(place);
		String buffer = Storage.readText(place,modestr);
		if( buffer == null ) buffer = "";

		if( isexpression == false ) {
			mScriptEngine.execScript(buffer, result, context, shortname, 0 );
		} else {
			mScriptEngine.evalExpression( buffer, result, context, shortname, 0 );
		}
	}
}
