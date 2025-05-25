package com.tilldawn.Control;

import com.tilldawn.Model.Ability;
import com.tilldawn.Model.AbilityType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class AbilityPool {
    private static boolean isInitialized = false;
    private static List<Ability> abilities;
    private static Random random;


    private AbilityPool() {}

    public static void initialize() {
        if (!isInitialized) {
            abilities = new ArrayList<>();
            random = new Random();

            for (AbilityType type : AbilityType.values()) {
                abilities.add(new Ability(type));
            }

            isInitialized = true;
        }
    }

    public static List<Ability> getAllAbilities() {
        ensureInitialized();
        return Collections.unmodifiableList(abilities);
    }

    public static List<Ability> getRandomAbilities(int n) {
        ensureInitialized();
        List<Ability> copy = new ArrayList<>(abilities);
        Collections.shuffle(copy, random);
        return copy.stream().limit(n).collect(Collectors.toList());
    }

    public static void addAbility(Ability ability) {
        ensureInitialized();
        abilities.add(ability);
    }

    public static void removeAbility(Ability ability) {
        ensureInitialized();
        abilities.remove(ability);
    }

    private static void ensureInitialized() {
        if (!isInitialized) {
            throw new IllegalStateException("AbilityPool is not initialized. Call AbilityPool.initialize() before using it.");
        }
    }

}
