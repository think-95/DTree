package file;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

/**
 * @author wzc
 * @version:2013 11 30 10:04:33 TODO:从文件中读取数据，并格式化数据
 */
public class ReadFile {

	private final int capacity = 5000000;
	private final int featureNum = 123;
	private final int dataNum = 31000;
	// for debugging
	private final boolean debug = false;
	private FileInputStream fin;
	private String filePath;
	private int line = 0;

	public int getLine() {
		if (line != 0)
			return line;
		else {
			System.out.println("please call format() first!");
			return 0;
		}
	}

	public ReadFile() {
		//训练数据集目录
		this.filePath = "E:\\a1a.train";
	}

	public ReadFile(String filePath) {
		// TODO Auto-generated constructor stub
		this.filePath = filePath;
	}

	private CharBuffer read() {
		try {
			fin = new FileInputStream(filePath);
			FileChannel fc = fin.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(capacity);
			CharBuffer chars = CharBuffer.allocate(capacity);
			fc.read(buffer);
			buffer.flip();
			chars = Charset.forName("UTF-8").decode(buffer);
			// 调试
			// chars.flip();
			// buffer.flip();
			/*
			 * while(buffer.hasRemaining()){ System.out.println(buffer.get()); }
			 */
			return chars;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public boolean[][] format() {
		CharBuffer buffer = this.read();
		boolean[][] data = new boolean[dataNum][featureNum + 1];

		int i = 0, j = 0;
		// int j=0;
		char tmp;

		for (; i < dataNum; i++) {
			for (; j < featureNum + 1; j++) {
				data[i][j] = false;
			}
		}

		i = 0;
		j = 0;

		while (buffer.hasRemaining()) {
			tmp = buffer.get();
			if (tmp == '\n') {
				i++;
				j = 0;
				continue;
			}
			if (tmp == ' ') {
				continue;
			}
			if (tmp == '-') {
				buffer.get();
				continue;
			}
			if (tmp == '+') {
				data[i][j] = true;
				if (debug)
					System.out.println(i + "," + j);
				buffer.get();
				continue;
			} else {
				j = Character.getNumericValue(tmp);
				tmp = buffer.get();
				while (tmp != ':') {
					j = j * 10 + Character.getNumericValue(tmp);
					tmp = buffer.get();
				}
				data[i][j] = true;
				buffer.get();
				if (debug)
					System.out.println(i + "," + j);
				j = 0;
			}
		}
		line = i;
		return data;
	}

	public static void main(String[] args) {
		ReadFile test = new ReadFile("E:\\a1a.t");
		test.format();
		System.out.println(test.getLine());
		System.out.println("done!");
	}
}
