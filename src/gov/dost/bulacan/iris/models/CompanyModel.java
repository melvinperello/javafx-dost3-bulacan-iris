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

import gov.dost.bulacan.iris.UnknownBusinessActivityException;

/**
 *
 * @author Jhon Melvin
 */
public class CompanyModel {

    public enum Sexuality {
        MALE, FEMALE;
    }

    /**
     * Business Activity Classification of the Company.
     */
    public static class BusinessActivity {

        public final static int FOOD_PROCESSING = 1;
        public final static int FURNITURES = 2;
        public final static int GIFTS_DECORS_HANDICRAFTS = 3;
        public final static int METALS_AND_ENGINEERING = 4;
        public final static int AGRICULTURE_MARINE_AQUACULTURE = 5;
        public final static int HEALTH_PRODUCTS_AND_PHARMACEUTICALS = 6;
        public final static int ICT_PRODUCTS = 7;
        public final static int OTHERS = 0;

        /**
         * Convert the value into String Equivalent.
         *
         * @param value Integer type business activity.
         * @return String Meaning.
         * @throws UnknownBusinessActivityException when an invalid value was
         * entered.
         */
        public static String getStringValue(int value) throws UnknownBusinessActivityException {
            switch (value) {
                case 0:
                    return "OTHERS";
                case 1:
                    return "FOOD PROCESSING";
                case 2:
                    return "FURNITURES";
                case 3:
                    return "GIFTS, DECORS, HANDICRAFT";
                case 4:
                    return "METALS AND ENGINEERING";
                case 5:
                    return "AGRICULTURE/MARINE/AQUACULTURE";
                case 6:
                    return "HEALTH PRODUCTS AND PHARMACEUTICALS";
                case 7:
                    return "INFORMATION AND COMMUNICATIONS TECHNOLOGY (ICT) PRODUCTS";
                default:
                    throw new UnknownBusinessActivityException("Invalid Integer Equivalent.");
            }
        }
    }
}
