package com.roby.demo.jdk.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferFile {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		RandomAccessFile aFile = new RandomAccessFile("d://ucc.sql", "rw");
		FileChannel fileChannel = aFile.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(255);
		int bytesRead = fileChannel.read(buf);
		while(bytesRead!=-1){
			buf.flip();  
		while(buf.hasRemaining()){
		      System.out.print((char) buf.get()); 
		  }
		buf.clear();
		bytesRead = fileChannel.read(buf);
		}
		aFile.close();
	}

}
