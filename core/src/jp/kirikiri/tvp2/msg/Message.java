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
/**
 * Message Strings ( these should be localized )
 */
package jp.kirikiri.tvp2.msg;

import jp.kirikiri.tjs2.TJSException;

public class Message {
	// Japanese localized messages
	static public final String InternalError = "内部エラーが発生しました: at %1 line %2";
	static public final String InvalidParam = "不正なパラメータです";
	static public final String NotImplemented = "未実装の機能を呼び出そうとしました";
	static public final String CannotOpenStorage = "ストレージ %1 を開くことができません";
	static public final String CannotFindStorage = "ストレージ %1 が見つかりません";
	static public final String ReadError = "読み込みエラーです。ファイルが破損している可能性や、デバイスからの読み込みに失敗した可能性があります";
	static public final String WriteError = "書き込みエラーです";
	static public final String SeekError = "シークに失敗しました。ファイルが破損している可能性や、デバイスからの読み込みに失敗した可能性があります";
	static public final String UnsupportedCipherMode = "%1 は未対応の暗号化形式か、データが破損しています";
	static public final String UnsupportedModeString = "認識できないモード文字列の指定です(%1)";

	static public final void throwExceptionMessage( final String msg ) throws TJSException {
		throw new TJSException(msg);
	}
	static public final void throwExceptionMessage( final String msg, final String p1, int num ) throws TJSException {
		String tmp = msg.replace( "%1", p1 );
		tmp = tmp.replace( "%2", String.valueOf(num) );
		throw new TJSException( tmp );
	}
	static public final void throwExceptionMessage( final String msg, final String p1 ) throws TJSException {
		String tmp = msg.replace( "%1", p1 );
		throw new TJSException( tmp );
	}
	static public final void throwExceptionMessage( final String msg, final String p1, final String p2 ) throws TJSException {
		String tmp = msg.replace( "%1", p1 );
		tmp = tmp.replace( "%2", p2 );
		throw new TJSException( tmp );
	}
	static public final String formatMessage( final String msg, final String p1, final String p2) {
		String tmp = msg.replace( "%1", p1 );
		return tmp.replace( "%2", p2 );
	}
};
