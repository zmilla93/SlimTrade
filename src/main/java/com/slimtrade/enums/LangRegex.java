package com.slimtrade.enums;

import java.util.regex.Pattern;

import static com.slimtrade.core.References.REGEX_CLIENT_PREFIX;
import static com.slimtrade.core.References.REGEX_QUICK_PASTE_PREFIX;

/**
 * An enum for storing language specific regex. Use 'CONTAINS_TEXT' to see if a message is valid to scan, then iterate through the respective array of patterns to extract trade data.
 * Since languages only support one containing text, the legacy English 'wtb' contains text is handled elsewhere and may eventually be dropped.
 */

public enum LangRegex {
    ENGLISH("like to buy", "has joined the area\\.", new String[]{
            "Hi, I'd like to buy your ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) for my ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+).)",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+)\\.?(?<bonusText>.+)?)",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+)\\.?(?<bonusText>.+)?)",
    }),
    FRENCH("t'acheter", "a rejoint la zone\\.", new String[]{
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) pour ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) dans la ligue (?<league>.+) \\(onglet de réserve \\\\?\"(?<stashtabName>.+)\" ; (?<stashX>\\d+)e en partant de la gauche, (?<stashY>\\d+)e en partant du haut\\)\\.?(?<bonusText>.+)?)",
            "Bonjour, je voudrais t'acheter ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) contre ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) dans la ligue (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) dans la ligue (?<league>.+) \\(onglet de réserve \\\\?\"(?<stashtabName>.+)\" ; (?<stashX>\\d+)e en partant de la gauche, (?<stashY>\\d+)e en partant du haut\\)\\.?(?<bonusText>.+)?)",
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) pour ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) dans la ligue (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) dans la ligue (?<league>.+)\\.?(?<bonusText>.+)?)",
    }),
    GERMAN("ich möchte", "hat das Gebiet betreten\\.", new String[]{
            "Hi, ich möchte '(?<itemName>.+)' zum angebotenen Preis von ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in der '?(?<league>.+)'?-Liga kaufen \\(Truhenfach \\\\?\"(?<stashtabName>.+)\"; Position: (?<stashX>\\d+) von links, (?<stashY>\\d+) von oben\\)\\.?(?<bonusText>.+)?)",
            "Hi, ich möchte '((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+))' zum angebotenen Preis von '((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+))' in der '?(?<league>.+)'?-Liga kaufen\\.?(?<bonusText>.+)?)",
            "Hi, ich möchte '(?<itemName>.+)' in der '?(?<league>.+)'?-Liga kaufen \\(Truhenfach \\\\?\"(?<stashtabName>.+)\"; Position: (?<stashX>\\d+) von links, (?<stashY>\\d+) von oben\\)\\.?(?<bonusText>.+)?)",
            "Hi, ich möchte '(?<itemName>.+)' zum angebotenen Preis von ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in der '?(?<league>.+)'?-Liga kaufen\\.?(?<bonusText>.+)?)",
            "Hi, ich möchte '(?<itemName>.+)' in der '?(?<league>.+)'?-Liga kaufen\\.?(?<bonusText>.+)?)",
    }),
    KOREAN("올려놓은", null, new String[]{
            "안녕하세요, (?<league>.+)\\(보관함 탭 \\\\?\"(?<stashtabName>.+)\", 위치: 왼쪽 (?<stashtabX>\\d+), 상단 (?<stashtabY>\\d+)\\)에 ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+))\\(으\\)로 올려놓은 (?<itemName>.+)\\(을\\)를 구매하고 싶습니다)",
            "안녕하세요, (?<league>.+)에 올려놓은(?<itemQuantity>\\d+) (?<itemName>.+) 오브\\(을\\)를 제 (?<priceQuantity>\\d+(\\.\\d+)?) (?<priceType>.+) 오브\\(으\\)로 구매하고 싶습니다)",
            "안녕하세요, (?<league>.+)\\(보관함 탭 \\\\?\"(?<stashtabName>.+)\", 위치: 왼쪽 (?<stashtabX>\\d+), 상단 (?<stashtabY>\\d+)\\)에 올려놓은 (?<itemName>.+)\\(을\\)를 구매하고 싶습니다)",
    }),
    PORTUGUESE("eu gostaria de comprar", "entrou na área\\.", new String[]{
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) na (?<league>.+) \\(aba do baú: \\\\?\"(?<stashtabName>.+)\"; posição: esquerda (?<stashX>\\d+), topo (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "Olá, eu gostaria de comprar seu(s) ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) pelo(s) meu(s) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) na (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) na (?<league>.+) \\(aba do baú: \\\\?\"(?<stashtabName>.+)\"; posição: esquerda (?<stashX>\\d+), topo (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) na (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) na (?<league>.+)\\.?(?<bonusText>.+)?)",
    }),
    RUSSIAN("хочу купить у вас", "присоединился\\.", new String[]{
            "Здравствуйте, хочу купить у вас (?<itemName>.+) за ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) в лиге (?<league>.+) \\(секция \\\\?\"(?<stashtabName>.+)\"; позиция: (?<stashX>\\d+) столбец, (?<stashY>\\d+) ряд\\)\\.?(?<bonusText>.+)?)",
            "Здравствуйте, хочу купить у вас ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) за ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) в лиге (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Здравствуйте, хочу купить у вас (?<itemName>.+) в лиге (?<league>.+) \\(секция \\\\?\"(?<stashtabName>.+)\"; позиция: (?<stashX>\\d+) столбец, (?<stashY>\\d+) ряд\\)\\.?(?<bonusText>.+)?)",
            "Здравствуйте, хочу купить у вас (?<itemName>.+) за ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) в лиге (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Здравствуйте, хочу купить у вас (?<itemName>.+) в лиге (?<league>.+)\\.?(?<bonusText>.+)?)",
    }),
    SPANISH("comprar tu", "se unió al área\\.", new String[]{
            "Hola, quisiera comprar tu (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) en (?<league>.+) \\(pestaña de alijo \\\\?\"(?<stashtabName>.+)\"; posición: izquierda(?<stashX>\\d+), arriba (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "Hola, me gustaría comprar tu(s) ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) por mi ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) en (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Hola, quisiera comprar tu (?<itemName>.+) en (?<league>.+) \\(pestaña de alijo \\\\?\"(?<stashtabName>.+)\"; posición: izquierda(?<stashX>\\d+), arriba (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "Hola, quisiera comprar tu (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) en (?<league>.+)\\.?(?<bonusText>.+)?)",
            "Hola, quisiera comprar tu (?<itemName>.+) en (?<league>.+)\\.?(?<bonusText>.+)?)",
    }),
    THAI("เราต้องการจะชื้อของคุณ", "เข้าสู่พื้นที่\\.", new String[]{
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน ราคา ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) ใน (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; ตำแหน่ง: ซ้าย (?<stashX>\\d+), บน (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; ตำแหน่ง: ซ้าย (?<stashX>\\d+), บน (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?)",
            "สวัสดี เรามีความต้องการจะชื้อ  ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) ของคุณ ฉันมี ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) ใน (?<league>.+)\\.?(?<bonusText>.+)?)",
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน ราคา ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) ใน (?<league>.+)\\.?(?<bonusText>.+)?)",
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน (?<league>.+)\\.?(?<bonusText>.+)?)",
    }),
    ;

    public final String CONTAINS_TEXT;
    public final Pattern[] CLIENT_PATTERNS;
    public final Pattern[] QUICK_PASTE_PATTERNS;
    public final Pattern JOINED_AREA_PATTERN;

    LangRegex(String text, Pattern[] clientPatterns, Pattern[] quickPastePatterns) {
        this.CONTAINS_TEXT = text;
        this.CLIENT_PATTERNS = clientPatterns;
        this.QUICK_PASTE_PATTERNS = quickPastePatterns;
        this.JOINED_AREA_PATTERN = null;
    }

    LangRegex(String containsText, String joinedArea, String[] phrases) {
        this.CONTAINS_TEXT = containsText;
        this.JOINED_AREA_PATTERN = Pattern.compile(".+ : (?<username>.+) " + joinedArea);
        CLIENT_PATTERNS = new Pattern[phrases.length];
        QUICK_PASTE_PATTERNS = new Pattern[phrases.length];
        for (int i = 0; i < phrases.length; i++) {
            CLIENT_PATTERNS[i] = Pattern.compile(REGEX_CLIENT_PREFIX + phrases[i]);
            QUICK_PASTE_PATTERNS[i] = Pattern.compile(REGEX_QUICK_PASTE_PREFIX + phrases[i]);
        }
    }


}
