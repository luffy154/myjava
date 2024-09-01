package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//将一个csv格式的数据文件中包含有单元格引用的内容替换为对应单元格内容的实际值。
//comma separated values(CSV) 逗号分隔值，csv格式的数据文件使用逗号“,”作为分隔符将各单元的内容进行分隔。
public class CSVProcessor {

    public static void main(String[] args) {
        String inputFile = "data.csv";
        String outputFile = "output.csv";

        try {
            // 读取CSV文件
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            // 将内容分割成行和单元格
            String[] rows = content.toString().split("\n");
            String[][] table = new String[rows.length][];
            for (int i = 0; i < rows.length; i++) {
                table[i] = rows[i].split(",");
            }

            // 处理单元格引用
            processReferences(table);

            // 写入新的CSV文件
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            for (String[] row : table) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
            writer.close();

            System.out.println("CSV文件处理完成!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processReferences(String[][] table) {
        int rows = table.length;
        int cols = table[0].length;
        Map<String, String> cellCache = new HashMap<>();

        // 首先计算单元格的实际值并缓存
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String value = table[i][j].trim();
                if (value.startsWith("=")) {
                    cellCache.put(getCellName(i, j), value.substring(1).trim());
                } else {
                    cellCache.put(getCellName(i, j), value);
                }
            }
        }

        // 替换引用
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String cellName = getCellName(i, j);
                String formula = cellCache.get(cellName);
                if (formula != null && formula.startsWith("SUM(")) {
                    String range = formula.substring(4, formula.length() - 1);
                    String[] parts = range.split(":");
                    int startRow = Integer.parseInt(parts[0].substring(1)) - 1;
                    int endRow = Integer.parseInt(parts[1].substring(1)) - 1;
                    int colIndex = parts[0].charAt(0) - 'A';
                    int sum = 0;
                    for (int k = startRow; k <= endRow; k++) {
                        sum += Integer.parseInt(cellCache.get(getCellName(k, colIndex)));
                    }
                    table[i][j] = String.valueOf(sum);
                } else if (formula != null) {
                    String replacedFormula = formula;
                    for (Map.Entry<String, String> entry : cellCache.entrySet()) {
                        String cellRef = entry.getKey();
                        String cellValue = entry.getValue();
                        replacedFormula = replacedFormula.replace(cellRef, cellValue);
                    }
                    table[i][j] = replacedFormula;
                }
            }
        }
    }

    private static String getCellName(int row, int col) {
        char colChar = (char) (col + 'A');
        return colChar + String.valueOf(row + 1);
    }
}
