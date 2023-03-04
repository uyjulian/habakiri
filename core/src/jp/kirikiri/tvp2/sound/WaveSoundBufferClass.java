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
package jp.kirikiri.tvp2.sound;

import jp.kirikiri.tjs2.Error;
import jp.kirikiri.tjs2.Interface;
import jp.kirikiri.tjs2.Dispatch2;
import jp.kirikiri.tjs2.NativeClass;
import jp.kirikiri.tjs2.NativeClassConstructor;
import jp.kirikiri.tjs2.NativeClassMethod;
import jp.kirikiri.tjs2.NativeClassProperty;
import jp.kirikiri.tjs2.NativeInstance;
import jp.kirikiri.tjs2.TJS;
import jp.kirikiri.tjs2.TJSException;
import jp.kirikiri.tjs2.Variant;
import jp.kirikiri.tjs2.VariantClosure;
import jp.kirikiri.tjs2.VariantException;
import jp.kirikiri.tvp2.TVP;
import jp.kirikiri.tvp2.base.EventManager;


public class WaveSoundBufferClass extends NativeClass {

	static int mClassID = -1;
	static private final String CLASS_NAME = "WaveSoundBuffer";

	protected NativeInstance createNativeInstance() {
		return new WaveSoundBufferNI();
	}

	public WaveSoundBufferClass() throws VariantException, TJSException {
		super( CLASS_NAME );
		final int NCM_CLASSID = TJS.registerNativeClass(CLASS_NAME);
		setClassID( NCM_CLASSID );
		mClassID = NCM_CLASSID;

		// constructor
		registerNCM( CLASS_NAME, new NativeClassConstructor() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws VariantException, TJSException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				int hr = _this.construct(param, objthis);
				if( hr < 0 ) return hr;
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );


		registerNCM( "finalize", TVP.ReturnOKMethod, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "open", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws TJSException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				if( param.length < 1) return Error.E_BADPARAMCOUNT;
				_this.open( param[0].asString() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "play", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.play();
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "stop", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.stop();
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "fade", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws TJSException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				if( param.length < 2) return Error.E_BADPARAMCOUNT;
				int to;
				int time;
				int delay = 0;
				to = param[0].asInteger();
				time = param[1].asInteger();
				if( param.length >= 3 && param[2].isVoid() != true )
					delay = param[2].asInteger();
				_this.fade( to, time, delay );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "stopFade", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.stopFade(false,true);
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "setPos", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				if( param.length < 3) return Error.E_BADPARAMCOUNT;
				double x, y , z;
				x = param[0].asDouble();
				y = param[1].asDouble();
				z = param[2].asDouble();

				_this.setPos( x, y, z );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );


		// event
		registerNCM( "onStatusChanged", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws TJSException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				VariantClosure obj = _this.getActionOwner();
				if( obj.mObject != null ) {
					if( param.length < 1 ) return Error.E_BADPARAMCOUNT;

					Dispatch2 evobj = EventManager.createEventObject( "onStatusChanged", objthis, objthis );
					Variant evval = new Variant(evobj, evobj);
					// set member
					evobj.propSet( Interface.MEMBERENSURE|Interface.IGNOREPROP, "status", param[0], evobj );

					Variant[] pevval = {evval};
					obj.funcCall( 0, EventManager.ActionName, result, pevval, null );
				}
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "onFadeCompleted", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws TJSException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				VariantClosure obj = _this.getActionOwner();
				if( obj.mObject != null ) {
					Dispatch2 evobj = EventManager.createEventObject( "onFadeCompleted", objthis, objthis );
					Variant evval = new Variant(evobj, evobj);

					Variant[] pevval = {evval};
					obj.funcCall( 0, EventManager.ActionName, result, pevval, null );
				}
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );

		registerNCM( "onLabel", new NativeClassMethod() {
			@Override
			protected int process(Variant result, Variant[] param, Dispatch2 objthis) throws TJSException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				VariantClosure obj = _this.getActionOwner();
				if( obj.mObject != null ) {
					if( param.length < 1 ) return Error.E_BADPARAMCOUNT;

					Dispatch2 evobj = EventManager.createEventObject( "onLabel", objthis, objthis );
					Variant evval = new Variant(evobj, evobj);
					// set member
					evobj.propSet( Interface.MEMBERENSURE|Interface.IGNOREPROP, "name", param[0], evobj );

					Variant[] pevval = {evval};
					obj.funcCall( 0, EventManager.ActionName, result, pevval, null );
				}
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitMethod, 0 );


		registerNCM( "position", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getPosition() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setPosition( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "samplePosition", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getSamplePosition() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setSamplePosition( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "paused", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getPaused() ? 1 : 0 );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setPaused( param.asBoolean() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "totalTime", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getTotalTime() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) {
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "looping", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getLooping() ? 1 : 0 );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setLooping( param.asBoolean() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "volume", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getVolume() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setVolume( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "volume2", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getVolume2() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setVolume2( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "pan", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getPan() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setPan( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "posX", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getPosX() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setPosX( param.asDouble() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "posY", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getPosY() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setPosY( param.asDouble() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "posZ", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getPosZ() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setPosZ( param.asDouble() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "status", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getStatusString() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) { return Error.E_ACCESSDENYED; }
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "frequency", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getFrequency() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				_this.setFrequency( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "bits", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getBitsPerSample() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) { return Error.E_ACCESSDENYED; }
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "channels", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				result.set( _this.getChannels() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) { return Error.E_ACCESSDENYED; }
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "flags", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				Dispatch2 dsp = _this.getWaveFlagsObject();
				result.set( dsp, dsp );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) { return Error.E_ACCESSDENYED; }
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "labels", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				Dispatch2 dsp = _this.getWaveLabelsObject();
				result.set( dsp, dsp );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) { return Error.E_ACCESSDENYED; }
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "filters", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				WaveSoundBufferNI _this = (WaveSoundBufferNI)objthis.getNativeInstance(mClassID);
				if( _this == null ) return Error.E_NATIVECLASSCRASH;
				Dispatch2 dsp = _this.getWaveFiltersObject();
				result.set( dsp, dsp );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) { return Error.E_ACCESSDENYED; }
		}, CLASS_NAME, Interface.nitProperty, 0 );

		registerNCM( "globalVolume", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				result.set( TVP.SoundMixer.getGlobalVolume() );
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) throws VariantException {
				TVP.SoundMixer.setGlobalVolume( param.asInteger() );
				return Error.S_OK;
			}
		}, CLASS_NAME, Interface.nitProperty, Interface.STATICMEMBER );

		// sgfmNeverMute = 0, sgfmMuteOnMinimize = 1, sgfmMuteOnDeactivate = 2
		registerNCM( "globalFocusMode", new NativeClassProperty() {
			@Override public int get(Variant result, Dispatch2 objthis) {
				result.set( 0 ); // sgfmNeverMute ミュートしない
				return Error.S_OK;
			}
			@Override public int set(Variant param, Dispatch2 objthis) {
				return Error.S_OK;	// 設定不可
			}
		}, CLASS_NAME, Interface.nitProperty, Interface.STATICMEMBER );
	}

}
