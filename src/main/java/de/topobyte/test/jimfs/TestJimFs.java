package de.topobyte.test.jimfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

public class TestJimFs
{

	public static void main(String[] args) throws IOException
	{
		FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
		Path foo = fs.getPath("/foo");
		Files.createDirectory(foo);

		Path hello = foo.resolve("hello.txt"); // /foo/hello.txt
		Files.write(hello, ImmutableList.of("hello world"),
				StandardCharsets.UTF_8);

		System.out.println("Reading using Files.readAllLines()");
		List<String> lines = Files.readAllLines(hello);
		System.out.println(lines);

		System.out.println("Reading using Files.newBufferedReader()");
		try {
			System.out.println(hello);
			BufferedReader reader = Files.newBufferedReader(foo);
			while (true) {
				String line = reader.readLine();
				if (line == null) {
					break;
				}
				System.out.println("line: " + line);
			}
		} catch (Exception e) {
			System.out.println("failed: " + e.getMessage());
		}

		System.out.println("Converting toFile()");
		try {
			File file = hello.toFile();
			System.out.println(file);
		} catch (Exception e) {
			System.out.println("failed: " + e.getMessage());
		}
	}

}
