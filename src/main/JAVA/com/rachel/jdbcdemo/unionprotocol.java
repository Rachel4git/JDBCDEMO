package com.rachel.jdbcdemo;

/**
 * Created by hd48552 on 2018/4/14.
 */
public class unionprotocol {

        // ��ˮ��
        private Long id;
        // ���Ե�����
        private String airline;
        // ���֤��
        private int union_airline_amount;
        // ׼��֤��
        private String union_airlines;
        // ѧ����
        private java.sql.Timestamp gmt_create;
        // ѧ����ַ
        private java.sql.Timestamp gmt_modified;
        // ���Է���.

        public long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }


//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }

    public String getAIRLINE() {
        return airline;
    }

        public  void  setAIRLINE(String airline){ this.airline= airline;}

        public int getUnion_airline_amount() {
            return union_airline_amount;
        }

        public void setUnion_airline_amount(int union_airline_amount) {
            this.union_airline_amount = union_airline_amount;
        }

        public String getUnion_airlines() {
            return union_airlines;
        }

        public void setUnion_airlines(String union_airlines) {
            this.union_airlines = union_airlines;
        }

        public java.sql.Timestamp getGmt_create() {
            return gmt_create;
        }

        public void setGmt_create(java.sql.Timestamp gmt_create) {
            this.gmt_create = gmt_create;
        }

        public java.sql.Timestamp getGmt_modified() {
            return gmt_modified;
        }

        public void setGmt_modified(java.sql.Timestamp gmt_modified) {
            this.gmt_modified = gmt_modified;
        }


        public unionprotocol(long id, int union_airline_amount, String airline, String union_airlines,
                             java.sql.Timestamp gmt_create, java.sql.Timestamp gmt_modified) {
            super();
            this.id = id;
            this.union_airline_amount = union_airline_amount;
            this.airline = airline;
            this.union_airlines = union_airlines;
            this.gmt_create = gmt_create;
            this.gmt_modified = gmt_modified;
        }

        public unionprotocol() {
            // TODO Auto-generated constructor stub
        }
        public unionprotocol(Long id,String airline,int union_airline_amount ,String union_airlines) {
            this.id = id;
            this.airline=airline;
            this.union_airline_amount = union_airline_amount;
            this.union_airlines=union_airlines;

        // TODO Auto-generated constructor stub
    }

        @Override
        public String toString() {
            return "unionprotocol [id=" + id + ", union_airline_amount=" + union_airline_amount + ", airline="
                    + airline + ", union_airlines=" + union_airlines + ", gmt_create="
                    + gmt_create + ", gmt_modified=" + gmt_modified
                    + "]\n";


    }

}
