/**
 * Information Retrieval Integrated System ( I.R.I.S. )
 * Republic of The Philippines, DOST Regional Office No. III
 * Provincial Science Technology Center, City of Malolos, Bulacan
 *
 * Afterschool Creatives "Captivating Creativity"
 *
 * Copyright 2018 Jhon Melvin Perello
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package gov.dost.bulacan.iris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Jhon Melvin
 */
public class PolarisText {

    public PolarisText() {
        this.appendMode = false;
        this.content = new StringBuilder("");
    }

    private boolean appendMode;
    private final StringBuilder content;

    public boolean isAppendMode() {
        return appendMode;
    }

    public void setAppendMode(boolean appendMode) {
        this.appendMode = appendMode;
    }

    public void save(File file) throws IOException {
        BufferedWriter buffWriter = null;
        try {
            final Writer writer = new OutputStreamWriter(new FileOutputStream(file, this.appendMode), StandardCharsets.UTF_8);
            /**
             * If you're writing large blocks of text at once (like entire
             * lines) then you probably won't notice a difference. If you have a
             * lot of code that appends a single character at a time, however, a
             * BufferedWriter will be much more efficient.
             *
             * As per andrew's comment below, the FileWriter actually uses its
             * own fixed-size 1024 byte buffer. This was confirmed by looking at
             * the source code. The BufferedWriter sources, on the other hand,
             * show that it uses and 8192 byte buffer size (default), which can
             * be configured by the user to any other desired size. So it seems
             * like the benefits of BufferedWriter vs. FileWriter are limited
             * to:
             *
             * Larger default buffer size. Ability to override/customize the
             * buffer size. And to further muddy the waters, the Java 6
             * implementation of OutputStreamWriter actually delegates to a
             * StreamEncoder, which uses its own buffer with a default size of
             * 8192 bytes. And the StreamEncoder buffer is user-configurable,
             * although there is no way to access it directly through the
             * enclosing OutputStreamWriter.
             *
             * Source:
             * https://stackoverflow.com/questions/6976893/in-java-what-is-the-advantage-of-using-bufferedwriter-to-append-to-a-file
             */
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(this.content.toString());
        } finally {
            if (buffWriter != null) {
                try {
                    buffWriter.flush();
                } catch (IOException e) {
                    // ignore
                }
                buffWriter.close();
            }
        }
    }

    public void open(File file) throws IOException {
        BufferedReader buffReader = null;
        try {
            final Reader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            buffReader = new BufferedReader(reader);
            //
            String line;
            while ((line = buffReader.readLine()) != null) {
                this.content.append(line + "\n");
            }
            // delete extra white space.
            if (this.content.charAt(this.content.length() - 1) == '\n') {
                this.content.deleteCharAt(this.content.length() - 1);
            }
        } finally {
            if (buffReader != null) {
                buffReader.close();
            }
        }
    }

    public void write(String text) {
        if (text != null) {
            this.content.append(text);
        }
    }

    public void writeln(String text) {
        this.write(text + "\n");
    }

    public String read() {
        return this.content.toString();
    }

}
