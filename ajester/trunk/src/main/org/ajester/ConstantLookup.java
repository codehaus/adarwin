package org.ajester;

import org.objectweb.asm.Constants;

import java.util.HashMap;
import java.util.Map;

public class ConstantLookup {
	private Map opcodeMap = new HashMap();
	
	public ConstantLookup() {
		opcodeMap.put(new Integer(Constants.NOP), "NOP");
		opcodeMap.put(new Integer(Constants.ACONST_NULL), "ACONST_NULL");
		opcodeMap.put(new Integer(Constants.ICONST_M1), "ICONST_M1");
		opcodeMap.put(new Integer(Constants.ICONST_0), "ICONST_0 (false)");
		opcodeMap.put(new Integer(Constants.ICONST_1), "ICONST_1 (true)");
		opcodeMap.put(new Integer(Constants.ICONST_2), "ICONST_2");
		opcodeMap.put(new Integer(Constants.ICONST_3), "ICONST_3");
		opcodeMap.put(new Integer(Constants.ICONST_4), "ICONST_4");
		opcodeMap.put(new Integer(Constants.ICONST_5), "ICONST_5");
		opcodeMap.put(new Integer(Constants.LCONST_0), "LCONST_0");
		opcodeMap.put(new Integer(Constants.LCONST_1), "LCONST_1");
		opcodeMap.put(new Integer(Constants.FCONST_0), "FCONST_0");
		opcodeMap.put(new Integer(Constants.FCONST_1), "FCONST_1");
		opcodeMap.put(new Integer(Constants.FCONST_2), "FCONST_2");
		opcodeMap.put(new Integer(Constants.DCONST_0), "DCONST_0");
		opcodeMap.put(new Integer(Constants.DCONST_1), "DCONST_1");
		opcodeMap.put(new Integer(Constants.BIPUSH), "BIPUSH");
		opcodeMap.put(new Integer(Constants.SIPUSH), "SIPUSH");
		opcodeMap.put(new Integer(Constants.LDC), "LDC");
		opcodeMap.put(new Integer(Constants.ILOAD), "ILOAD");
		opcodeMap.put(new Integer(Constants.LLOAD), "LLOAD");
		opcodeMap.put(new Integer(Constants.FLOAD), "FLOAD");
		opcodeMap.put(new Integer(Constants.DLOAD), "DLOAD");
		opcodeMap.put(new Integer(Constants.ALOAD), "ALOAD");
		opcodeMap.put(new Integer(Constants.IALOAD), "IALOAD");
		opcodeMap.put(new Integer(Constants.LALOAD), "LALOAD");
		opcodeMap.put(new Integer(Constants.FALOAD), "FALOAD");
		opcodeMap.put(new Integer(Constants.DALOAD), "DALOAD");
		opcodeMap.put(new Integer(Constants.AALOAD), "AALOAD");
		opcodeMap.put(new Integer(Constants.BALOAD), "BALOAD");
		opcodeMap.put(new Integer(Constants.CALOAD), "CALOAD");
		opcodeMap.put(new Integer(Constants.SALOAD), "SALOAD");
		opcodeMap.put(new Integer(Constants.ISTORE), "ISTORE");
		opcodeMap.put(new Integer(Constants.LSTORE), "LSTORE");
		opcodeMap.put(new Integer(Constants.FSTORE), "FSTORE");
		opcodeMap.put(new Integer(Constants.DSTORE), "DSTORE");
		opcodeMap.put(new Integer(Constants.ASTORE), "ASTORE");
		opcodeMap.put(new Integer(Constants.IASTORE), "IASTORE");
		opcodeMap.put(new Integer(Constants.LASTORE), "LASTORE");
		opcodeMap.put(new Integer(Constants.FASTORE), "FASTORE");
		opcodeMap.put(new Integer(Constants.DASTORE), "DASTORE");
		opcodeMap.put(new Integer(Constants.AASTORE), "AASTORE");
		opcodeMap.put(new Integer(Constants.BASTORE), "BASTORE");
		opcodeMap.put(new Integer(Constants.CASTORE), "CASTORE");
		opcodeMap.put(new Integer(Constants.SASTORE), "SASTORE");
		opcodeMap.put(new Integer(Constants.POP), "POP");
		opcodeMap.put(new Integer(Constants.POP2), "POP2");
		opcodeMap.put(new Integer(Constants.DUP), "DUP");
		opcodeMap.put(new Integer(Constants.DUP_X1), "DUP_X1");
		opcodeMap.put(new Integer(Constants.DUP_X2), "DUP_X2");
		opcodeMap.put(new Integer(Constants.DUP2), "DUP2");
		opcodeMap.put(new Integer(Constants.DUP2_X1), "DUP2_X1");
		opcodeMap.put(new Integer(Constants.DUP2_X2), "DUP2_X2");
		opcodeMap.put(new Integer(Constants.SWAP), "SWAP");
		opcodeMap.put(new Integer(Constants.IADD), "IADD");
		opcodeMap.put(new Integer(Constants.LADD), "LADD");
		opcodeMap.put(new Integer(Constants.FADD), "FADD");
		opcodeMap.put(new Integer(Constants.DADD), "DADD");
		opcodeMap.put(new Integer(Constants.ISUB), "ISUB");
		opcodeMap.put(new Integer(Constants.LSUB), "LSUB");
		opcodeMap.put(new Integer(Constants.FSUB), "FSUB");
		opcodeMap.put(new Integer(Constants.DSUB), "DSUB");
		opcodeMap.put(new Integer(Constants.IMUL), "IMUL");
		opcodeMap.put(new Integer(Constants.LMUL), "LMUL");
		opcodeMap.put(new Integer(Constants.FMUL), "FMUL");
		opcodeMap.put(new Integer(Constants.DMUL), "DMUL");
		opcodeMap.put(new Integer(Constants.IDIV), "IDIV");
		opcodeMap.put(new Integer(Constants.LDIV), "LDIV");
		opcodeMap.put(new Integer(Constants.FDIV), "FDIV");
		opcodeMap.put(new Integer(Constants.DDIV), "DDIV");
		opcodeMap.put(new Integer(Constants.IREM), "IREM");
		opcodeMap.put(new Integer(Constants.LREM), "LREM");
		opcodeMap.put(new Integer(Constants.FREM), "FREM");
		opcodeMap.put(new Integer(Constants.DREM), "DREM");
		opcodeMap.put(new Integer(Constants.INEG), "INEG");
		opcodeMap.put(new Integer(Constants.LNEG), "LNEG");
		opcodeMap.put(new Integer(Constants.FNEG), "FNEG");
		opcodeMap.put(new Integer(Constants.DNEG), "DNEG");
		opcodeMap.put(new Integer(Constants.ISHL), "ISHL");
		opcodeMap.put(new Integer(Constants.LSHL), "LSHL");
		opcodeMap.put(new Integer(Constants.ISHR), "ISHR");
		opcodeMap.put(new Integer(Constants.LSHR), "LSHR");
		opcodeMap.put(new Integer(Constants.IUSHR), "IUSHR");
		opcodeMap.put(new Integer(Constants.LUSHR), "LUSHR");
		opcodeMap.put(new Integer(Constants.IAND), "IAND");
		opcodeMap.put(new Integer(Constants.LAND), "LAND");
		opcodeMap.put(new Integer(Constants.IOR), "IOR");
		opcodeMap.put(new Integer(Constants.LOR), "LOR");
		opcodeMap.put(new Integer(Constants.IXOR), "IXOR");
		opcodeMap.put(new Integer(Constants.LXOR), "LXOR");
		opcodeMap.put(new Integer(Constants.IINC), "IINC");
		opcodeMap.put(new Integer(Constants.I2L), "I2L");
		opcodeMap.put(new Integer(Constants.I2F), "I2F");
		opcodeMap.put(new Integer(Constants.I2D), "I2D");
		opcodeMap.put(new Integer(Constants.L2I), "L2I");
		opcodeMap.put(new Integer(Constants.L2F), "L2F");
		opcodeMap.put(new Integer(Constants.L2D), "L2D");
		opcodeMap.put(new Integer(Constants.F2I), "F2I");
		opcodeMap.put(new Integer(Constants.F2L), "F2L");
		opcodeMap.put(new Integer(Constants.F2D), "F2D");
		opcodeMap.put(new Integer(Constants.D2I), "D2I");
		opcodeMap.put(new Integer(Constants.D2L), "D2L");
		opcodeMap.put(new Integer(Constants.D2F), "D2F");
		opcodeMap.put(new Integer(Constants.I2B), "I2B");
		opcodeMap.put(new Integer(Constants.I2C), "I2C");
		opcodeMap.put(new Integer(Constants.I2S), "I2S");
		opcodeMap.put(new Integer(Constants.LCMP), "LCMP");
		opcodeMap.put(new Integer(Constants.FCMPL), "FCMPL");
		opcodeMap.put(new Integer(Constants.FCMPG), "FCMPG");
		opcodeMap.put(new Integer(Constants.DCMPL), "DCMPL");
		opcodeMap.put(new Integer(Constants.DCMPG), "DCMPG");
		opcodeMap.put(new Integer(Constants.IFEQ), "IFEQ");
		opcodeMap.put(new Integer(Constants.IFNE), "IFNE");
		opcodeMap.put(new Integer(Constants.IFLT), "IFLT");
		opcodeMap.put(new Integer(Constants.IFGE), "IFGE");
		opcodeMap.put(new Integer(Constants.IFGT), "IFGT");
		opcodeMap.put(new Integer(Constants.IFLE), "IFLE");
		opcodeMap.put(new Integer(Constants.IF_ICMPEQ), "IF_ICMPEQ");
		opcodeMap.put(new Integer(Constants.IF_ICMPNE), "IF_ICMPNE");
		opcodeMap.put(new Integer(Constants.IF_ICMPLT), "IF_ICMPLT");
		opcodeMap.put(new Integer(Constants.IF_ICMPGE), "IF_ICMPGE");
		opcodeMap.put(new Integer(Constants.IF_ICMPGT), "IF_ICMPGT");
		opcodeMap.put(new Integer(Constants.IF_ICMPLE), "IF_ICMPLE");
		opcodeMap.put(new Integer(Constants.IF_ACMPEQ), "IF_ACMPEQ");
		opcodeMap.put(new Integer(Constants.IF_ACMPNE), "IF_ACMPNE");
		opcodeMap.put(new Integer(Constants.GOTO), "GOTO");
		opcodeMap.put(new Integer(Constants.JSR), "JSR");
		opcodeMap.put(new Integer(Constants.RET), "RET");
		opcodeMap.put(new Integer(Constants.TABLESWITCH), "TABLESWITCH");
		opcodeMap.put(new Integer(Constants.LOOKUPSWITCH), "LOOKUPSWITCH");
		opcodeMap.put(new Integer(Constants.IRETURN), "IRETURN");
		opcodeMap.put(new Integer(Constants.LRETURN), "LRETURN");
		opcodeMap.put(new Integer(Constants.FRETURN), "FRETURN");
		opcodeMap.put(new Integer(Constants.DRETURN), "DRETURN");
		opcodeMap.put(new Integer(Constants.ARETURN), "ARETURN");
		opcodeMap.put(new Integer(Constants.RETURN), "RETURN");
		opcodeMap.put(new Integer(Constants.GETSTATIC), "GETSTATIC");
		opcodeMap.put(new Integer(Constants.PUTSTATIC), "PUTSTATIC");
		opcodeMap.put(new Integer(Constants.GETFIELD), "GETFIELD");
		opcodeMap.put(new Integer(Constants.PUTFIELD), "PUTFIELD");
		opcodeMap.put(new Integer(Constants.INVOKEVIRTUAL), "INVOKEVIRTUAL");
		opcodeMap.put(new Integer(Constants.INVOKESPECIAL), "INVOKESPECIAL");
		opcodeMap.put(new Integer(Constants.INVOKESTATIC), "INVOKESTATIC");
		opcodeMap.put(new Integer(Constants.INVOKEINTERFACE), "INVOKEINTERFACE");
		opcodeMap.put(new Integer(Constants.NEW), "NEW");
		opcodeMap.put(new Integer(Constants.NEWARRAY), "NEWARRAY");
		opcodeMap.put(new Integer(Constants.ANEWARRAY), "ANEWARRAY");
		opcodeMap.put(new Integer(Constants.ARRAYLENGTH), "ARRAYLENGTH");
		opcodeMap.put(new Integer(Constants.ATHROW), "ATHROW");
		opcodeMap.put(new Integer(Constants.CHECKCAST), "CHECKCAST");
		opcodeMap.put(new Integer(Constants.INSTANCEOF), "INSTANCEOF");
		opcodeMap.put(new Integer(Constants.MONITORENTER), "MONITORENTER");
		opcodeMap.put(new Integer(Constants.MONITOREXIT), "MONITOREXIT");
		opcodeMap.put(new Integer(Constants.MULTIANEWARRAY), "MULTIANEWARRAY");
		opcodeMap.put(new Integer(Constants.IFNULL), "IFNULL");
		opcodeMap.put(new Integer(Constants.IFNONNULL), "IFNONNULL");
	}
	
	public String getOpcodeName(int opcode) {
		return (String) opcodeMap.get(new Integer(opcode));
	}
}
