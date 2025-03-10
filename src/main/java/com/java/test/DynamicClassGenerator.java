package com.java.test;

/**
 * @ClassName:DynamicClassGenerator
 * @author: qm
 * @Description:
 * @date:2025-02-11
 */
import org.objectweb.asm.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class DynamicClassGenerator implements Opcodes {

    public static void main(String[] args) throws IOException {
        DynamicClassGenerator generator = new DynamicClassGenerator();
        generator.generateClass();
    }

    private void generateClass() throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        String className = "com/example/DynamicClass";
        String classInternalName = className.replace('.', '/');

        // 定义类
        classWriter.visit(V1_8, ACC_PUBLIC, classInternalName, null, "java/lang/Object", null);

        // 定义字段
        classWriter.visitField(ACC_PRIVATE, "value", "I", null, null).visitEnd();

        // 生成构造函数
        generateConstructor(classWriter);

        // 生成静态方法
        generateStaticMethod(classWriter);

        // 生成实例方法
        generateInstanceMethod(classWriter);

        // 生成异常处理方法
        generateExceptionHandlingMethod(classWriter);

        // 生成动态方法
        generateDynamicMethod(classWriter);

        // 完成类定义
        byte[] classBytes = classWriter.toByteArray();

        // 保存类到磁盘
        saveClass(classBytes, className);
    }

    private void generateConstructor(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(I)V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitFieldInsn(PUTFIELD, "com/example/DynamicClass", "value", "I");
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

    private void generateStaticMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "computeSum", "(II)I", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ILOAD, 0);
        methodVisitor.visitVarInsn(ILOAD, 1);
        methodVisitor.visitInsn(IADD);
        methodVisitor.visitInsn(IRETURN);
        methodVisitor.visitMaxs(2, 2);
        methodVisitor.visitEnd();
    }

    private void generateInstanceMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "incrementValue", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitFieldInsn(GETFIELD, "com/example/DynamicClass", "value", "I");
        methodVisitor.visitInsn(ICONST_1);
        methodVisitor.visitInsn(IADD);
        methodVisitor.visitFieldInsn(PUTFIELD, "com/example/DynamicClass", "value", "I");
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();
    }

    private void generateExceptionHandlingMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "processWithException", "()V", null, new String[]{ "java/lang/Exception" });
        methodVisitor.visitCode();
        methodVisitor.visitTryCatchBlock(
                new Label(), new Label(), new Label(), "java/lang/Exception"
        );
        methodVisitor.visitVarInsn(ALOAD, 0);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "com/example/DynamicClass", "unsafeMethod", "()V");
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitLabel(new Label());
        methodVisitor.visitVarInsn(ALOAD, 1);
        methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Object", "toString", "()Ljava/lang/String;");
        methodVisitor.visitMethodInsn(INVOKESTATIC, "java/lang/System", "err", "(Ljava/lang/String;)V");
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(2, 1);
        methodVisitor.visitEnd();
    }

    private void generateDynamicMethod(ClassWriter classWriter) {
        MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "dynamicCall", "()V", null, null);
        methodVisitor.visitCode();
        methodVisitor.visitInvokeDynamicInsn(
                "call",
                "(Ljava/lang/String;)Ljava/lang/Object;",
                new Handle(
                        Opcodes.H_INVOKESTATIC,
                        "java/lang/invoke/MethodHandles",
                        "lookup",
                        "java/lang/invoke/MethodHandles/Lookup"
                ),
                new Object[]{ "action", "execute" }
        );
        methodVisitor.visitInsn(RETURN);
        methodVisitor.visitMaxs(1, 1);
        methodVisitor.visitEnd();
    }

    private void saveClass(byte[] classBytes, String className) throws IOException {
        String path = "src/main/java/"+className.replace('.', '/') + ".class";
        File file = new File(path);
        // 打印文件的全路径名
        System.out.println("文件的全路径名: " + file.getAbsolutePath());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(classBytes);
        }
    }
}
