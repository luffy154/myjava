package com.java.test;

import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @ClassName:SpecTest
 * @author: qm
 * @Description:
 * @date:2025-02-17
 */
public class SpecTest {
    public static void main(String[] args) throws Exception {
        // 指定命名曲线
        String curveName = "secp256r1"; // 例如 secp256r1, secp384r1, secp521r1 等
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("secp256k1");

        ECNamedCurveSpec ecNamedCurveSpec = new ECNamedCurveSpec(curveName, spec.getCurve(), spec.getG(), spec.getN(), spec.getH());

        // 使用命名曲线初始化密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC", "BC"); // 使用 Bouncy Castle 提供者
        keyPairGenerator.initialize(ecNamedCurveSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        System.out.println("Private Key: " + privateKey);
        System.out.println("Public Key: " + publicKey);
    }
}
