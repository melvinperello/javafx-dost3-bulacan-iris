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

import java.util.HashMap;

/**
 *
 * @author Jhon Melvin
 */
public class FileExtensions extends HashMap<String, String> {

    public final static String PPT = "Microsoft Power Point";
    public final static String DOC = "Microsoft Word";
    public final static String XLS = "Microsoft Excel";
    public final static String PSD = "Photoshop Document";
    public final static String PDF = "Portable Document Format";
    public final static String ZIP = "Compressed File";
    public final static String IMG = "Image File";
    public final static String VID = "Video File";
    public final static String MUSIC = "Audio File";
    public final static String FILE = "File";

    private static final long serialVersionUID = 1L;

    private final void putMany(String[] fileExtensions, String type) {
        for (String fileExtension : fileExtensions) {
            this.put(fileExtension, type);
        }
    }

    private FileExtensions() {
        this.putMany(new String[]{
            "pptx", "ppt"
        }, PPT);

        this.putMany(new String[]{
            "docx", "doc"
        }, DOC);

        this.putMany(new String[]{
            "xlsx", "xls"
        }, XLS);

        this.put("psd", PSD);
        this.put("pdf", PDF);

        //
        this.putMany(new String[]{
            "arc", "arj", "as", "b64", "btoa", "bz"
        }, ZIP);

        this.putMany(new String[]{
            "cab", "cpt", "gz", "hqx", "iso", "lha"
        }, ZIP);

        this.putMany(new String[]{
            "lzh", "mim", "mme", "pak", "pf", "rar", "rpm"
        }, ZIP);

        this.putMany(new String[]{
            "sea", "sit", "sitx", "gz", "tbz", "tbz2"
        }, ZIP);

        this.putMany(new String[]{
            "tgz", "uu", "uue", "z", "zip", "zipx", "zoo", "7z", "7zip"
        }, ZIP);
        //

        this.putMany(new String[]{
            "avi", "flv", "wmv", "mov", "mp4", "mkv"
        }, VID);

        this.putMany(new String[]{
            "jpg", "jpeg", "tif", "tiff", "png", "gif", "mkv", "bmp"
        }, IMG);

        //
        this.putMany(new String[]{
            "pcm", "wav", "aiff", "mp3", "aac", "ogg", "wma", "flac", "alac", "wma"
        }, MUSIC);
    }

    private static final FileExtensions _instance = new FileExtensions();

    public static String recognizeFile(String ext) {
        String type = _instance.get(ext);
        if (type == null) {
            type = FILE;
        }
        return type;
    }

    public static String getDisplayIcon(String f_ext) {
        switch (f_ext) {
            case FileExtensions.DOC:
                return "word_128.png";
            case FileExtensions.FILE:
                return "default_128.png";
            case FileExtensions.IMG:
                return "image_128.png";
            case FileExtensions.PDF:
                return "pdf_128.png";
            case FileExtensions.PPT:
                return "powerpoint_128.png";
            case FileExtensions.PSD:
                return "psd_128.png";
            case FileExtensions.VID:
                return "video_128.png";
            case FileExtensions.XLS:
                return "excel_128.png";
            case FileExtensions.ZIP:
                return "zip_128.png";
            case FileExtensions.MUSIC:
                return "music_128.png";
            default:
                return "default_128.png";
        }
    }

}
