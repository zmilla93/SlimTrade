package com.slimtrade.gui.enums;

import com.slimtrade.core.References;

import javax.swing.*;
import java.awt.*;

public enum POEImage {
    NONE,
    ALBINO_RHOA_FEATHER(),
    ARMOURERS_SCRAP("Armourer's Scrap", "armor", "armour"),
    AWAKENED_SEXTANT(),
    BESTIARY_ORB(),
    BLACKSMITHS_WHETSTONE("Blacksmith's Whetstone", "blacksmith", "whetstone"),
    BLESSED_ORB("blessed"),
    BLESSING_OF_CHAYULA(),
    BLESSING_OF_ESH(),
    BLESSING_OF_TUL(),
    BLESSING_OF_UUL_NETOL(),
    BLESSING_OF_XOPH(),
    CARTOGRAPHERS_CHISEL("Cartographer's Chisel", "chisel", "cart"),
    CHAOS_ORB("chaos", "choas"),
    CHROMATIC_ORB("chrome", "chrom"),
    CRUSADERS_EXALTED_ORB(),
    DIVINE_ORB("divine", "div"),
    EXALTED_ORB("exalt, ex"),
    GEMCUTTERS_PRISM("Gemcutter's Prism", "gcp", "gemc"),
    GLASSBLOWERS_BAUBLE("Glassblower's Bauble", "glass", "bauble"),
    HUNTERS_EXALTED_ORB(),
    JEWELLERS_ORB("Jeweller's Orb", "jeweller"),
    MIRROR_OF_KALANDRA("mirror", "kalandra"),
    ORB_OF_ALCHEMY("alchemy", "alch"),
    ORB_OF_ALTERATION("alt"),
    ORB_OF_ANNULMENT(""),
    ORB_OF_AUGMENTATION(),
    ORB_OF_CHANCE("chance", "chanc"),
    ORB_OF_FUSING("fusing", "fuse"),
    ORB_OF_REGRET("regret"),
    ORB_OF_SCOURING("scour"),
    ORB_OF_TRANSMUTATION("transmut"),
    ORB_OF_BINDING(),
    ORB_OF_HORIZONS(),
    PORTAL_SCROLL("portal"),
    PRIME_SEXTANT(),
    REDEEMERS_EXALTED_ORB(),
    REGAL_ORB("regal"),
    SCROLL_OF_WISDOM("wisdom"),
    SILVER_COIN(),
    SIMPLE_SEXTANT(),
    STACKED_DECK(),
    VAAL_ORB("vaal"),
    WARLORDS_EXALTED_ORB(),
    ALCHEMY_SHARD(),
    ALTERATION_SHARD(),
    SCROLL_FRAGMENT(),
    SPLINTER_OF_CHAYULA(),
    SPLINTER_OF_ESH(),
    SPLINTER_OF_TUL(),
    SPLINTER_OF_UUL_NETOL(),
    SPLINTER_OF_XOPH(),
    TRANSMUTATION_SHARD(),
    VIAL_OF_AWAKENING(),
    VIAL_OF_CONSEQUENCE(),
    VIAL_OF_DOMINANCE(),
    VIAL_OF_FATE(),
    VIAL_OF_SACRIFICE(),
    VIAL_OF_SUMMONING(),
    VIAL_OF_THE_GHOST(),
    VIAL_OF_THE_RITUAL(),
    VIAL_OF_TRANSCENDENCE(),
    ANCIENT_ORB("ancient-orb"),
    ANCIENT_SHARD(),
    ANNULMENT_SHARD(),
    BINDING_SHARD(),
    CHAOS_SHARD(),
    ENGINEERS_ORB("Engineer's Orb", "engineers-orb"),
    ENGINEERS_SHARD(),
    EXALTED_SHARD(),
    HARBINGERS_ORB("Harbinger's Orb", "harbingers-orb"),
    HARBINGERS_SHARD(),
    HORIZON_SHARD(),
    MIRROR_SHARD(),
    REGAL_SHARD(),
    PERANDUS_COIN("coin", "coins", "perandus"),
    ABERRANT_FOSSIL(),
    AETHERIC_FOSSIL(),
    BLOODSTAINED_FOSSIL(),
    BOUND_FOSSIL(),
    CORRODED_FOSSIL(),
    DENSE_FOSSIL(),
    ENCHANTED_FOSSIL(),
    ENCRUSTED_FOSSIL(),
    FACETED_FOSSIL(),
    FRACTURED_FOSSIL(),
    FRIGID_FOSSIL(),
    GILDED_FOSSIL(),
    GLYPHIC_FOSSIL(),
    HOLLOW_FOSSIL(),
    JAGGED_FOSSIL(),
    LUCENT_FOSSIL(),
    METALLIC_FOSSIL(),
    PERFECT_FOSSIL(),
    PRISMATIC_FOSSIL(),
    PRISTINE_FOSSIL(),
    SANCTIFIED_FOSSIL(),
    SCORCHED_FOSSIL(),
    SERRATED_FOSSIL(),
    SHUDDERING_FOSSIL(),
    TANGLED_FOSSIL(),
    POTENT_ALCHEMICAL_RESONATOR(),
    POTENT_CHAOTIC_RESONATOR(),
    POWERFUL_ALCHEMICAL_RESONATOR(),
    POWERFUL_CHAOTIC_RESONATOR(),
    PRIME_ALCHEMICAL_RESONATOR(),
    PRIME_CHAOTIC_RESONATOR(),
    PRIMITIVE_ALCHEMICAL_RESONATOR(),
    PRIMITIVE_CHAOTIC_RESONATOR(),
    AMBER_OIL(),
    AZURE_OIL(),
    BLACK_OIL(),
    CLEAR_OIL(),
    CRIMSON_OIL(),
    GOLDEN_OIL(),
    OPALESCENT_OIL(),
    SEPIA_OIL(),
    SILVER_OIL(),
    TEAL_OIL(),
    VERDANT_OIL(),
    VIOLET_OIL(),
    ABRASIVE_CATALYST(),
    FERTILE_CATALYST(),
    IMBUED_CATALYST(),
    INTRINSIC_CATALYST(),
    PRISMATIC_CATALYST(),
    TEMPERING_CATALYST(),
    TURBULENT_CATALYST(),
    ETERNAL_ORB(),
    UNSHAPING_ORB(),
    APPRENTICE_CARTOGRAPHERS_SEAL(),
    JOURNEYMAN_CARTOGRAPHERS_SEAL(),
    MASTER_CARTOGRAPHERS_SEAL(),
    SIMPLE_ROPE_NET(),
    REINFORCED_ROPE_NET(),
    STRONG_ROPE_NET(),
    SIMPLE_IRON_NET(),
    REINFORCED_IRON_NET(),
    STRONG_IRON_NET(),
    SIMPLE_STEEL_NET(),
    REINFORCED_STEEL_NET(),
    STRONG_STEEL_NET(),
    THAUMATURGICAL_NET(),
    NECROMANCY_NET(),
    ;


//    private static final double IMAGE_SCALE = 1;
//    private static final int DEFAULT_SIZE = 20;
//    private static int imageSize = (int) (DEFAULT_SIZE * IMAGE_SCALE);

    private Image image;
    private int cachedSize = 0;
    private String[] tags = null;

    POEImage(String... inTags) {
        if(inTags.length > 0) {
            tags = new String[inTags.length + 1];
            tags[0] = this.name().toLowerCase().replaceAll("_", " ");
            for(int i = 0; i<inTags.length; i++) {
                tags[i+1] = inTags[i];
            }
        }
    }

    public boolean isValid() {
        if(tags != null) {
            return true;
        }
        return false;
    }

    private String getPath() {
        StringBuilder builder = new StringBuilder();
        boolean cap = true;
        for(char c : this.name().toCharArray()) {
            if(cap){
                builder.append(Character.toUpperCase(c));
                cap = false;
            } else if (c == '_'){
                builder.append(c);
                cap = true;
            } else {
                builder.append(Character.toLowerCase(c));
            }
        }
        return "currency/" + builder.toString().replaceAll("Of", "of") + ".png";
    }

    public String[] getTags() {
        return tags;
    }

    public Image getImage() {
        return getImage(References.DEFAULT_IMAGE_SIZE);
    }

    public Image getImage(int size) {
        if (image == null || size != this.cachedSize) {
            System.out.println("\tGenerating Image : " + getPath());
            cachedSize = size;
            image = new ImageIcon(getClass().getClassLoader().getResource(getPath())).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        }
        return image;
    }

}
