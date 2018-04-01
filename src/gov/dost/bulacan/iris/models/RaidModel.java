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

import gov.dost.bulacan.iris.Context;
import gov.dost.bulacan.iris.models.ext.TableAuditor;
import java.util.Date;
import org.afterschoolcreatives.polaris.java.sql.orm.PolarisRecord;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Column;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.PrimaryKey;
import org.afterschoolcreatives.polaris.java.sql.orm.annotations.Table;

/**
 *
 * @author Jhon Melvin
 */
@Table(RaidModel.TABLE)
public class RaidModel extends PolarisRecord implements TableAuditor {

    public final static String TABLE = "file_table";
    public final static String FILE_ID = "file_id"; // primary key
    public final static String FILE_DISPLAY_NAME = "file_display_name";
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
    @Column(FILE_PATH)
    private String path;
    @Column(FILE_NAME)
    private String name;
    @Column(FILE_EXT)
    private String extenstion;
    @Column(FILE_SIZE)
    private Long size;
    @Column(FILE_HASH)
    private String hash;

    public RaidModel() {
        this.displayName = "";
        this.path = "";
        this.name = "";
        this.extenstion = "";
        this.size = 0L;
        this.hash = "";
    }

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    //==========================================================================
    // ANNEX-A. Table Audit
    //==========================================================================
    // Table Columns
    //--------------------------------------------------------------------------
    public final static String CREATED_BY = "created_by";
    public final static String CREATED_AT = "created_at";
//    public final static String UPDATED_BY = "updated_by";
//    public final static String UPDATED_AT = "updated_at";
    public final static String DELETED_BY = "deleted_by";
    public final static String DELETED_AT = "deleted_at";
    //--------------------------------------------------------------------------
    // Fields
    //--------------------------------------------------------------------------
    @Column(CREATED_BY)
    private String createdBy;

    @Column(CREATED_AT)
    private java.util.Date createdAt;

//    @Column(UPDATED_BY)
//    private String updatedBy;
//
//    @Column(UPDATED_AT)
//    private java.util.Date updatedAt;
    @Column(DELETED_BY)
    private String deletedBy;

    @Column(DELETED_AT)
    private java.util.Date deletedAt;

    //--------------------------------------------------------------------------
    // Getters
    //--------------------------------------------------------------------------
    @Override
    public String getCreatedBy() {
        return (this.createdBy == null) ? "" : this.createdBy;
    }

    @Override
    public java.util.Date getCreatedAt() {
        return (this.createdAt == null) ? null : new Date(this.createdAt.getTime());
    }

    @Override
    public String getUpdatedBy() {
//        return (this.updatedBy == null) ? "" : this.updatedBy;
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public java.util.Date getUpdatedAt() {
//        return (this.updatedAt == null) ? null : new Date(this.updatedAt.getTime());
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public String getDeletedBy() {
        return (this.deletedBy == null) ? "" : this.deletedBy;
    }

    @Override
    public java.util.Date getDeletedAt() {
        return (this.deletedAt == null) ? null : new Date(this.deletedAt.getTime());
    }

    //--------------------------------------------------------------------------
    // Setters
    //--------------------------------------------------------------------------
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = (createdBy == null) ? "" : createdBy;
    }

    @Override
    public void setCreatedAt(java.util.Date createdAt) {
        this.createdAt = (createdAt == null) ? null : new Date(createdAt.getTime());
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
//        this.updatedBy = (updatedBy == null) ? "" : updatedBy;
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public void setUpdatedAt(java.util.Date updatedAt) {
//        this.updatedAt = (updatedAt == null) ? null : new Date(updatedAt.getTime());
        throw new UnsupportedOperationException("NO UPDATE");
    }

    @Override
    public void setDeletedBy(String deletedBy) {
        this.deletedBy = (deletedBy == null) ? "" : deletedBy;
    }

    @Override
    public void setDeletedAt(java.util.Date deletedAt) {
        this.deletedAt = (deletedAt == null) ? null : new Date(deletedAt.getTime());
    }
    // </ANNEX-A. Table Audit> 

    @Override
    public String auditLatest() {
        if (this.getCreatedAt() != null) {
            return "Entry Created by [ " + this.getCreatedBy() + " ] "
                    + " at " + Context.app().getDateFormat12().format(this.getCreatedAt());
        } else {
            return "No Audit History";
        }
    }

    @Override
    public void auditUpdate() {
        throw new UnsupportedOperationException("NO UPDATE");
    }

}
