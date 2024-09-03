package org.example;

import java.util.HashMap;
import java.util.Map;

class PizzaLink {
    int val;
    PizzaLink left, right;
    boolean isEmpty = false;

    PizzaLink(int val) {
        this.val = val;
    }
}

public class AAATest {

    private static int calcCount(PizzaLink pizzaLink, int counter, int N) {
        //取数计数器
        counter++;
        pizzaLink.isEmpty = true;
        System.out.print(pizzaLink.val+"+"+counter+"  ");
        PizzaLink left = pizzaLink.left, right = pizzaLink.right;
        int size = 0;
        for (int i = 0; i < N; i++) {
            if (!left.isEmpty) {
                break;
            }
            left = left.left;
        }

        for (int i = 0; i < N; i++) {
            if (!right.isEmpty) {
                break;
            }
            right = right.right;
        }

        if (left.isEmpty && right.isEmpty) {
            counter--;
            pizzaLink.isEmpty = false;
            return pizzaLink.val;
        }

        //偶数次取货
        if (counter % 2 == 0) {
            if (!left.isEmpty) {
                size = Math.max(calcCount(left, counter, N), size);
            }
            if (!right.isEmpty) {
                size = Math.max(calcCount(right, counter, N), size);
            }
        } else {
            if(left.val>right.val){
                size+=calcCount(left, counter, N);
            }else{
                size+=calcCount(right, counter, N);
            }
            size += pizzaLink.val;
        }
        counter--;
        pizzaLink.isEmpty = false;
        return size;
    }

    public static void main(String[] args) {
        int[] pizzas = {8,2,10,5,7};
        Map<Integer, PizzaLink> map = new HashMap<>();
        for (int i = 0; i < pizzas.length; i++) {
            PizzaLink pizzaLink = new PizzaLink(pizzas[i]);
            map.put(i, pizzaLink);
        }
        for (int i = 0; i < pizzas.length; i++) {
            if (i == 0) {
                map.get(i).left = map.get(pizzas.length - 1);
                map.get(i).right = map.get(i + 1);
            } else if (i == pizzas.length - 1) {
                map.get(i).left = map.get(i - 1);
                map.get(i).right = map.get(0);
            } else {
                map.get(i).left = map.get(i - 1);
                map.get(i).right = map.get(i + 1);
            }
        }

        for (int i = 0; i < pizzas.length; i++) {
            int counter = 0;
            int N = pizzas.length;
            System.out.println(calcCount(map.get(i), counter,pizzas.length));
        }

    }


}
