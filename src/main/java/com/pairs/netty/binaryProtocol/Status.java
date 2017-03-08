package com.pairs.netty.binaryProtocol;

/**
 * Created by hupeng on 2017/3/2.
 */
public class Status {
    public static final short NO_ERROR = 0x0000;
    public static final short KEY_NOT_FOUND = 0x0001;
    public static final short KEY_EXISTS = 0x0002;
    public static final short VALUE_TOO_LARGE = 0x0003;
    public static final short INVALID_ARGUMENTS = 0x0004;
    public static final short ITEM_NOT_STORED = 0x0005;
    public static final short INC_DEC_NON_NUM_VAL = 0x0006;

    public static void main(String[] args) {
        String input = "232302FE4C5A5954455441573645313034343531340100761101180F001D010101010064000006A40030012C19010100140000020101010C01F400781E00DC00320301E00064000200013802261900190C01F4170104010BB84E20054006D1020B01D1E50E0601010AF0010103E80101BE0101780701480000000100000000010000000001000000000100000000FA";
        String regex = "(.{2})";
        input = input.replaceAll (regex, "$1 ");
        System.out.println (input);
    }
}
