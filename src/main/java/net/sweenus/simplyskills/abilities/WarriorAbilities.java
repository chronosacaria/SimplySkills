package net.sweenus.simplyskills.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ShieldItem;
import net.sweenus.simplyskills.SimplySkills;
import net.sweenus.simplyskills.registry.EffectRegistry;
import net.sweenus.simplyskills.util.HelperMethods;
import net.sweenus.simplyskills.util.SkillReferencePosition;

public class WarriorAbilities {

    public static void passiveWarriorSpellbreaker(PlayerEntity player) {
        int spellbreakingDuration = SimplySkills.warriorConfig.passiveWarriorSpellbreakerDuration;
        int spellbreakingChance = SimplySkills.warriorConfig.passiveWarriorSpellbreakerChance;
        if (player.getRandom().nextInt(100) < spellbreakingChance) {
            player.addStatusEffect(new StatusEffectInstance(EffectRegistry.SPELLBREAKING, spellbreakingDuration));
        }
    }

    public static void passiveWarriorDeathDefy(PlayerEntity player) {
        int deathDefyFrequency = SimplySkills.warriorConfig.passiveWarriorDeathDefyFrequency;
        int deathDefyAmplifierPerTenPercentHealth = SimplySkills.warriorConfig.passiveWarriorDeathDefyAmplifierPerTenPercentHealth;
        int regen = 0;

        int healthThreshold = SimplySkills.warriorConfig.passiveWarriorDeathDefyHealthThreshold;
        if (player.age % deathDefyFrequency == 0) {
            float playerHealthPercent = ((player.getHealth() / player.getMaxHealth()) * 100);
            if (playerHealthPercent < healthThreshold) {
                if (playerHealthPercent < (healthThreshold - 10))
                    regen = regen + deathDefyAmplifierPerTenPercentHealth;
                if (playerHealthPercent < (healthThreshold - 20))
                    regen = regen + (deathDefyAmplifierPerTenPercentHealth * 2);

                player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION,
                        deathDefyFrequency + 5, regen));
                if (player.hasStatusEffect(StatusEffects.REGENERATION)
                        && player.getStatusEffect(StatusEffects.REGENERATION).getAmplifier() > 0)
                    HelperMethods.incrementStatusEffect(player, EffectRegistry.EXHAUSTION,
                            deathDefyFrequency + 60, regen +10, 99);
            }
        }
    }

    public static void passiveWarriorGoliath(PlayerEntity player) {
        player.addStatusEffect(new StatusEffectInstance(EffectRegistry.EARTHSHAKER, 200));
    }

    public static void passiveWarriorArmorMastery(PlayerEntity player) {
        int armorMasteryThreshold = SimplySkills.warriorConfig.passiveWarriorArmorMasteryArmorThreshold - 1;
        int armorMasteryChance = SimplySkills.warriorConfig.passiveWarriorArmorMasteryChance;
        int heavyArmorMasteryDuration = SimplySkills.warriorConfig.passiveWarriorHeavyArmorMasteryDuration;
        int heavyArmorMasteryAmplifier = SimplySkills.warriorConfig.passiveWarriorHeavyArmorMasteryAmplifier;
        int mediumArmorMasteryDuration = SimplySkills.warriorConfig.passiveWarriorMediumArmorMasteryDuration;
        int mediumArmorMasteryAmplifier = SimplySkills.warriorConfig.passiveWarriorMediumArmorMasteryAmplifier;

        if (player.getRandom().nextInt(100) < armorMasteryChance) {
            if (player.getArmor() > armorMasteryThreshold
                    && HelperMethods.isUnlocked("simplyskills",
                    SkillReferencePosition.warriorHeavyArmorMastery, player)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH,
                        heavyArmorMasteryDuration, heavyArmorMasteryAmplifier));
            } else if (HelperMethods.isUnlocked("simplyskills",
                    SkillReferencePosition.warriorMediumArmorMastery, player)){
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION,
                        mediumArmorMasteryDuration, mediumArmorMasteryAmplifier));
            }
        }
    }

    public static void passiveWarriorFrenzy(PlayerEntity player) {
        int frenzyDuration = SimplySkills.warriorConfig.passiveWarriorFrenzyExhaustionDuration;
        int frenzyStacks = SimplySkills.warriorConfig.passiveWarriorFrenzyExhaustionStacks;

        HelperMethods.incrementStatusEffect(player, EffectRegistry.EXHAUSTION, frenzyDuration, frenzyStacks, 99);
    }

    public static void passiveWarriorShieldMastery(PlayerEntity player) {
        int shieldMasteryFrequency = SimplySkills.warriorConfig.passiveWarriorShieldMasteryFrequency;
        int shieldMasteryWeaknessAmplifier = SimplySkills.warriorConfig.passiveWarriorShieldMasteryWeaknessAmplifier;
        int shieldMasteryResistanceAmplifier = SimplySkills.warriorConfig.passiveWarriorShieldMasteryResistanceAmplifier;
        int shieldMasteryResistanceAmplifierPerTier = SimplySkills.warriorConfig.passiveWarriorShieldMasteryResistanceAmplifierPerTier;


        if (player.age % shieldMasteryFrequency == 0) {
            if (player.getOffHandStack() != null) {
                if (player.getOffHandStack().getItem() instanceof ShieldItem) {

                    int mastery = shieldMasteryResistanceAmplifier;

                    if (HelperMethods.isUnlocked("simplyskills",
                            SkillReferencePosition.bulwarkShieldMasterySkilled, player))
                        mastery = mastery + (shieldMasteryResistanceAmplifierPerTier * 2);
                    else if (HelperMethods.isUnlocked("simplyskills",
                            SkillReferencePosition.bulwarkShieldMasteryProficient, player))
                        mastery = mastery + shieldMasteryResistanceAmplifierPerTier;

                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,
                            shieldMasteryFrequency + 5, mastery));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,
                            shieldMasteryFrequency + 5, shieldMasteryWeaknessAmplifier));
                }
            }
        }
    }

    public static void passiveWarriorRebuke(PlayerEntity player, LivingEntity attacker) {
        int rebukeChance = SimplySkills.warriorConfig.passiveWarriorRebukeChance;
        int rebukeWeaknessDuration = SimplySkills.warriorConfig.passiveWarriorRebukeWeaknessDuration;
        int rebukeWeaknessAmplifier = SimplySkills.warriorConfig.passiveWarriorRebukeWeaknessAmplifier;
        if (player.getRandom().nextInt(100) < rebukeChance) {
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS,
                    rebukeWeaknessDuration, rebukeWeaknessAmplifier));
        }
    }

}
