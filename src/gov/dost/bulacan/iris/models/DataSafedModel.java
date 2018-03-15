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
package gov.dost.bulacan.iris.models;

import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 *
 * @author Jhon Melvin
 */
@Table(DataSafedModel.TABLE)
public class DataSafedModel extends PolarisRecord {

    public final static String TABLE = "file_table";
    public final static String FILE_ID = "file_id"; // primary key
    public final static String FILE_DISPLAY_NAME = "file_display_name";
    public final static String FILE_DESCRIPTION = "file_description";
    public final static String FILE_PATH = "file_path"; // folder location
    public final static String FILE_NAME = "file_name"; // file name
    public final static String FILE_EXT = "file_ext"; // file extension
    public final static String FILE_SIZE = "file_size";
    public final static String FILE_HASH = "file_hash";

    @PrimaryKey
    @Column(FILE_ID)
    private String id;
    @Column(FILE_DISPLAY_NAME)
    private String displayName;
    @Column(FILE_DESCRIPTION)
    private String description;
    @Column(FILE_PATH)
    private String path;
    @Column(FILE_NAME)
    private String name;
    @Column(FILE_EXT)
    private String extenstion;
    @Column(FILE_SIZE)
    private String size;
    @Column(FILE_HASH)
    private String hash;

    //--------------------------------------------------------------------------
    // Getters and Setters
    //--------------------------------------------------------------------------
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtenstion() {
        return extenstion;
    }

    public void setExtenstion(String extenstion) {
        this.extenstion = extenstion;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

}
