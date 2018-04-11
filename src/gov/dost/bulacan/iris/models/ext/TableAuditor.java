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
package gov.dost.bulacan.iris.models.ext;

import gov.dost.bulacan.iris.Context;

/**
 *
 * @author Jhon Melvin
 */
public interface TableAuditor {

    //==========================================================================
    // Getters
    //==========================================================================
    String getCreatedBy();

    java.util.Date getCreatedAt();

    String getUpdatedBy();

    java.util.Date getUpdatedAt();

    String getDeletedBy();

    java.util.Date getDeletedAt();

    //==========================================================================
    // Setters
    //==========================================================================
    void setCreatedBy(String createdBy);

    void setCreatedAt(java.util.Date createdAt);

    void setUpdatedBy(String updatedBy);

    void setUpdatedAt(java.util.Date updatedAt);

    void setDeletedBy(String deletedBy);

    void setDeletedAt(java.util.Date deletedAt);

    //==========================================================================
    // Audit
    //==========================================================================
    public default void auditCreate() {
        this.setCreatedBy(Context.app().getAuditUser());
        this.setCreatedAt(Context.app().getServerDate());
    }

    public default void auditUpdate() {
        this.setUpdatedBy(Context.app().getAuditUser());
        this.setUpdatedAt(Context.app().getServerDate());
    }

    public default void auditDelete() {
        this.setDeletedBy(Context.app().getAuditUser());
        this.setDeletedAt(Context.app().getServerDate());
    }

    public default String auditToString() {
        if (this.getUpdatedAt() == null) {
            if (this.getCreatedAt() != null) {
                return Context.getDateFormat12().format(this.getCreatedAt()) + " by " + this.getCreatedBy();
            } else {
                return "No Audit History";
            }
        } else {
            return Context.getDateFormat12().format(this.getUpdatedAt()) + " by " + this.getUpdatedBy();
        }
    }

    public default String auditDetailedToString() {
        if (this.getUpdatedAt() == null) {
            if (this.getCreatedAt() != null) {
                return "CREATED AT " + Context.getDateFormat12().format(this.getCreatedAt()) + " by " + this.getCreatedBy();
            } else {
                return "No Audit History";
            }
        } else {
            return "UPDATED AT " + Context.getDateFormat12().format(this.getUpdatedAt()) + " by " + this.getUpdatedBy();
        }
    }
}
