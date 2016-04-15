/* 
 * PROJECT: NyARToolkit(Extension)
 * --------------------------------------------------------------------------------
 *
 * The NyARToolkit is Java edition ARToolKit class library.
 * Copyright (C)2008-2012 Ryo Iizuka
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as publishe
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * For further information please contact.
 *	http://nyatla.jp/nyatoolkit/
 *	<airmail(at)ebony.plala.or.jp> or <nyatla(at)nyatla.jp>
 * 
 */
package jp.nyatla.nyartoolkit.nftsystem;

import jp.nyatla.nyartoolkit.core.param.NyARParam;
import jp.nyatla.nyartoolkit.core.types.NyARIntSize;

/**
 * このインタフェイスは、NyARNftSystemのコンフィギュレーションオブジェクトに使用します。
 * {@link NyARMarkerSystem}は、このインタフェイスを継承したクラスから、動作に必要なオブジェクトや定数を取得します。
 */
public interface INyARNftSystemConfig
{
	/**
	 * ARToolKitのカメラパラメータのオブジェクトを返します。
	 * @return
	 * [readonly]
	 * 参照値です。
	 */
	public NyARParam getNyARParam();
	/**
	 * このコンフィギュレーションのスクリーンサイズを返します。
	 * @return
	 * [readonly]
	 * 参照値です。
	 */
	public NyARIntSize getScreenSize();
}