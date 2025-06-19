package com.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodChangeClassAdapter extends ClassVisitor implements Opcodes {
    public MethodChangeClassAdapter(ClassVisitor cv) {
        super(Opcodes.ASM4, cv);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        // 当方法名为execute时，修改方法名为execute1
        if (cv != null && "execute".equals(name)) {
            return cv.visitMethod(access, name + "1", desc, signature, exceptions);
        }

        // 此处的changeMethodContent即为需要修改的方法 ，修改方法內容
        if ("changeMethodContent".equals(name)) {
            // 先得到原始的方法
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            MethodVisitor newMethod = null;
            // 访问需要修改的方法
            newMethod = new AsmMethodVisit(mv);
            return newMethod;
        }
        if (cv != null) {
            return cv.visitMethod(access, name, desc, signature, exceptions);
        }
        return null;
    }
}