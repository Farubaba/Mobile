package com.silence.rootfeature.utils;

/**
 * 进制转换工具类
 * @author liangzg
 *
 */
public class RadixConverter {

	public static final String hexPrefix = "0x";
	public static final String hexString =  "0123456789abcdef";
	/**
	 * 该数组的下标值，正好等于该位置的二进制字符串值
	 */
	public static final String[] binaryArray =
	        {"0000","0001","0010","0011",  
	        "0100","0101","0110","0111",  
	        "1000","1001","1010","1011",  
	        "1100","1101","1110","1111"}; 
	
	/**
	 * 功能描述：使用Integer.toBinaryString()方法将【一个字节的数据】转化成对应的二进制字符串。<br>
	 * <br>
	 * Java中Integer占据4个字节，所以使用Integer.toBinaryString()转化出的二进制位有 4 * 8 = 32 位。<br>
	 * 最高位是符号位，符号位为0,表示正数，可省略。符号位为1，表示负数，负数存储的是补码，计算负数的值需先还原成原码。<br>
	 * <br>
	 * <pre>
	 * 例如：
	 * byte byteValue = 9；
	 * 
	 * 1.假设 byteValue = 9
	 *	  输出结果字符串为: 1001
	 * 2.假设 byteValue = -114 
	 * 	（负数存储补码，值换算规则为： 正数的二进制-->取反-->加1 = 负数的补码 ）
	 * 	（负数补码还原，其换算规则为： 补码-->减1-->取反 = 原码数值）
	 *    输出结果字符串为（负数存储的是补码）：11111111111111111111111110001110
	 *    负数求值：
	 *    1）减1	： 11111111111111111111111110001101
	 *    2) 取反	： 00000000000000000000000001110010 = 64+32+16+0+0+2+0 = 114
	 * </pre>
	 * @param b
	 * @return
	 * 
	 * @deprecated 参考{@link #byte2BinaryString(StringBuilder, byte)}
	 */
	@Deprecated
	private static String byte2BinaryString(byte b) {
		return Integer.toBinaryString((int)b);
	}
	
	/**
	 * 一个字节等于8位二进制，由于4位二进制数对应一个16进制数，<br>
	 * 我们可以将一个字节划分为【高4位】和【低四位】来分别计算出他们对应的16进制值，然后再拼接在一起。<br>
	 * 相比{@link RadixConverter#byte2BinaryString(byte)}}不会转换出多余字节。<br>
	 * @param b 
	 * @return
	 */
	public static StringBuilder byte2BinaryString(final StringBuilder sb, byte b) {
		if(sb != null) {
			//处理高四位，高四位10进制值如下，可以用做 binaryArray的下标
			int index = (b & 0xF0) >> 4;
			sb.append(binaryArray[index]);
			//处理低四位，低四位10进制值如下，可以用做 binaryArray的下标
			index = (b & 0x0F);
			sb.append(binaryArray[index]);
		}
		return sb;
	}
	
	/**
	 * 功能描述：将一个byte数组转化成对应的二进制字符串，相比{@link #byteArray2HexString(byte[])}}不会有多余的字节产生。
	 * 
	 * @param sb
	 * @param array
	 * @return
	 */
	public static StringBuilder byteArray2BinaryString(final StringBuilder sb, byte[] array) {
		if(array != null) {
			for(byte b : array) {
				byte2BinaryString(sb, b);
			}
		}
		return sb;
	}
	
	/**
	 * 相比{@link Integer#toBinaryString(int)}不会转换出多余字节。
	 * @param b
	 * @return
	 */
	public static StringBuilder byte2HexString(final StringBuilder sb, byte b) {
		//处理正负数的逻辑不一样，负数的值需要先将补码还原成原码
		//负数补码还原，其换算规则为： 补码-->减1-->取反 = 原码数值
		if(sb != null) {
			//处理高四位，高四位10进制值如下，可以用做 binaryArray的下标
			byte index = (byte) ((b & 0xF0) >> 4);
			sb.append(hexString.charAt(index));
			//处理低四位，低四位10进制值如下，可以用做 binaryArray的下标
			index = (byte) (b & 0x0F);
			sb.append(hexString.charAt(index));
		}
		return sb;
	}
	
	/**
	 *功能描述：将字节数组转化成对应的十六进制字符串
	 * @param sb
	 * @param array
	 * @return
	 */
	public static StringBuilder byteArray2HexString(final StringBuilder sb, byte[] array) {
		if(array != null) {
			for(byte b : array) {
				byte2HexString(sb, b);
			}
		}
		return sb;
	}
	
	/**
	 * 功能描述：将16进制字符串转化成byte[]
	 * 0x028e
	 * @param sourceHexString
	 * @return
	 */
	public static byte[] hexString2ByteArray(String sourceHexString){  
        //sourceHexString的长度对2取整，作为bytes的长度  
        int len = sourceHexString.length()/2;  
        byte[] bytes = new byte[len];  
        byte high = 0;//字节高四位  
        byte low = 0;//字节低四位  
        for(int i=0;i<len;i++){  
             //右移四位得到高位  
             high = (byte)((hexString.indexOf(sourceHexString.charAt(2*i)))<<4);  
             low = (byte)hexString.indexOf(sourceHexString.charAt(2*i+1));  
             bytes[i] = (byte) (high|low);//高地位做或运算  
        }  
        return bytes;  
    }
	
	/**
	 * 功能描述：将16进制字符串转化为2进制字符串
	 * @param sb
	 * @param sourceHexString
	 * @return
	 */
	public static StringBuilder hexString2BinaryString(StringBuilder sb, String sourceHexString){
        return byteArray2BinaryString(sb, hexString2ByteArray(sourceHexString));
    }

	/**
	 * 判断一个给定的字符串是否符合二进制字符串标准,只包含二进制的0和1
	 *
	 * 特例： 101010满足十六进制格式，也满足二进制格式，还满足10进制格式
	 *
	 * 所以该方法，只能用于验证格式是否满足，并不能确定被验证的值，到底是什么进制。
	 * @param binaryString
	 * @return
	 */
	public static boolean isBinaryStringFormat(String binaryString) {
		if(binaryString != null) {
			for(int i=0 ; i < binaryString.length(); i++) {
				char c = binaryString.charAt(i);
				if(c != '0' && c != '1') {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 判断一个给定的字符串是否符合十六进制字符串标准，只包含十六进制的
	 * 0123456789abcdef
	 *
	 * 特例： 101010满足十六进制格式，也满足二进制格式，还满足10进制格式
	 *
	 * 所以该方法，只能用于验证格式是否满足，并不能确定被验证的值，到底是什么进制。
	 * @param hexString
	 * @return
	 */
	public static boolean isHexStringFormat(String hexString) {
		char[] chars = RadixConverter.hexString.toCharArray();
		if(hexString != null) {
			if(hexString.startsWith(hexPrefix)) {
				hexString = hexString.substring(hexPrefix.length());
			}

			for(int i=0; i< hexString.length(); i++) {
				char c = hexString.charAt(i);
				boolean find = false;
				for(char dictionary : chars) {
					if(c == dictionary) {
						find = true;
						break;
					}
				}
				//只要有一个字符查询不到就返回失败
				if(!find) {
					return false;
				}
			}
			//所有字符都在字典中查询到
			return true;

		}
		return false;
	}
}
