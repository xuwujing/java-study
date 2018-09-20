/**
 * 
 */
package com.pancm.commons.apache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
* @Title: compressTest
* @Description:
* 压缩测试 
* @Version:1.0.0  
* @author pancm
* @date 2018年5月14日
*/
public class CompressTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		test();
	}

	/**
	 * 压缩文件测试
	 */
	private static void test() {

		// 创建压缩对象
		ZipArchiveEntry entry = new ZipArchiveEntry("CompressTest");
		// 要压缩的文件
		File f = new File("d:\\user.txt");
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			// 输出的对象 压缩的文件
			ZipArchiveOutputStream zipOutput = new ZipArchiveOutputStream(new File("d:\\user.zip"));
			zipOutput.putArchiveEntry(entry);
			int i = 0, j;
			while ((j = fis.read()) != -1) {
				zipOutput.write(j);
				i++;
			}
			System.out.println("压缩成功!遍历了:" + i + "次");
			zipOutput.closeArchiveEntry();
			zipOutput.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
