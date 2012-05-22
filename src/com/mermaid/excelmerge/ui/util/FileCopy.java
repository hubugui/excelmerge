package com.mermaid.excelmerge.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileCopy {
	public static void copy(String oldPath, String newPath) {
		try {
			File oldfile = new File(oldPath);

			if (oldfile.exists()) {
				InputStream in = new FileInputStream(oldPath);
				FileOutputStream out = new FileOutputStream(newPath);

				byte[] buffer = new byte[1444];
				int bytesum = 0;
				int byteread = 0;

				while ((byteread = in.read(buffer)) != -1) {
					bytesum += byteread;
					out.write(buffer, 0, byteread);
				}
				in.close();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
