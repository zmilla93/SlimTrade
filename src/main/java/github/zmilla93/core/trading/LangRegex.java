package github.zmilla93.core.trading;

import github.zmilla93.core.References;
import github.zmilla93.modules.updater.ZLogger;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * An enum for storing language specific regex. Use 'CONTAINS_TEXT' to see if a message is valid to scan, then iterate through the respective array of patterns to extract trade data.
 */
public enum LangRegex {

    ENGLISH("like to buy", "has joined the area\\.", "You have entered (?<zone>.+)\\.",
            "to", "from", "DND mode is now OFF", "DND mode is now ON", new String[]{
            "Hi, I'd like to buy your ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) for my ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+).",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; position: left (?<stashX>\\d+), top (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) (listed for|for my) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in (?<league>.+)\\.?(?<bonusText>.+)?",
            "((Hi, )?(I would|I'd) like to buy your|wtb) (?<itemName>.+) in (?<league>.+)\\.?(?<bonusText>.+)?",
    }),
    FRENCH("t'acheter", "a rejoint la zone\\.", null,
            "à", "de", "Le mode Ne Pas Déranger (DND) est désactivé", "Le mode Ne Pas Déranger (DND) est désormais activé", new String[]{
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) pour ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) dans la ligue (?<league>.+) \\(onglet de réserve \\\\?\"(?<stashtabName>.+)\" ; (?<stashX>\\d+)e en partant de la gauche, (?<stashY>\\d+)e en partant du haut\\)\\.?(?<bonusText>.+)?",
            "Bonjour, je voudrais t'acheter ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) contre ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) dans la ligue (?<league>.+)\\.?(?<bonusText>.+)?",
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) dans la ligue (?<league>.+) \\(onglet de réserve \\\\?\"(?<stashtabName>.+)\" ; (?<stashX>\\d+)e en partant de la gauche, (?<stashY>\\d+)e en partant du haut\\)\\.?(?<bonusText>.+)?",
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) pour ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) dans la ligue (?<league>.+)\\.?(?<bonusText>.+)?",
            "Bonjour, je souhaiterais t'acheter (?<itemName>.+) dans la ligue (?<league>.+)\\.?(?<bonusText>.+)?",
    }),
    GERMAN("ich möchte", "hat das Gebiet betreten\\.", null,
            "an", "von", "Nicht-Stören-Modus ist nun AUS", "Nicht-Stören-Modus ist nun AN", new String[]{
            "Hi, ich möchte '(?<itemName>.+)' zum angebotenen Preis von ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in der '?(?<league>.+)'?-Liga kaufen \\(Truhenfach \\\\?\"(?<stashtabName>.+)\"; Position: (?<stashX>\\d+) von links, (?<stashY>\\d+) von oben\\)\\.?(?<bonusText>.+)?",
            "Hi, ich möchte '((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+))' zum angebotenen Preis von '((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+))' in der '?(?<league>.+)'?-Liga kaufen\\.?(?<bonusText>.+)?",
            "Hi, ich möchte '(?<itemName>.+)' in der '?(?<league>.+)'?-Liga kaufen \\(Truhenfach \\\\?\"(?<stashtabName>.+)\"; Position: (?<stashX>\\d+) von links, (?<stashY>\\d+) von oben\\)\\.?(?<bonusText>.+)?",
            "Hi, ich möchte '(?<itemName>.+)' zum angebotenen Preis von ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) in der '?(?<league>.+)'?-Liga kaufen\\.?(?<bonusText>.+)?",
            "Hi, ich möchte '(?<itemName>.+)' in der '?(?<league>.+)'?-Liga kaufen\\.?(?<bonusText>.+)?",
    }),
    KOREAN("올려놓은", null, null,
            null, null, "방해 금지 모드를 해제했습니다", "방해 금지 모드를 설정했습니다", new String[]{
            "안녕하세요, (?<league>.+)\\(보관함 탭 \\\\?\"(?<stashtabName>.+)\", 위치: 왼쪽 (?<stashtabX>\\d+), 상단 (?<stashtabY>\\d+)\\)에 ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+))\\(으\\)로 올려놓은 (?<itemName>.+)\\(을\\)를 구매하고 싶습니다",
            "안녕하세요, (?<league>.+)에 올려놓은(?<itemQuantity>\\d+) (?<itemName>.+) 오브\\(을\\)를 제 (?<priceQuantity>\\d+(\\.\\d+)?) (?<priceType>.+) 오브\\(으\\)로 구매하고 싶습니다",
            "안녕하세요, (?<league>.+)\\(보관함 탭 \\\\?\"(?<stashtabName>.+)\", 위치: 왼쪽 (?<stashtabX>\\d+), 상단 (?<stashtabY>\\d+)\\)에 올려놓은 (?<itemName>.+)\\(을\\)를 구매하고 싶습니다",
    }),
    PORTUGUESE("eu gostaria de comprar", "entrou na área\\.", null,
            "para", "de", "Modo NP Desativado", "Modo NP Ativado", new String[]{
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) na (?<league>.+) \\(aba do baú: \\\\?\"(?<stashtabName>.+)\"; posição: esquerda (?<stashX>\\d+), topo (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "Olá, eu gostaria de comprar seu(s) ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) pelo(s) meu(s) ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) na (?<league>.+)\\.?(?<bonusText>.+)?",
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) na (?<league>.+) \\(aba do baú: \\\\?\"(?<stashtabName>.+)\"; posição: esquerda (?<stashX>\\d+), topo (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) na (?<league>.+)\\.?(?<bonusText>.+)?",
            "Olá, eu gostaria de comprar o seu item (?<itemName>.+) na (?<league>.+)\\.?(?<bonusText>.+)?",
    }),
    RUSSIAN("хочу купить у вас", "присоединился\\.", null,
            "кому", "от кого", "выключен", "включён. Авто-ответ", new String[]{
            "Здравствуйте, хочу купить у вас (?<itemName>.+) за ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) в лиге (?<league>.+) \\(секция \\\\?\"(?<stashtabName>.+)\"; позиция: (?<stashX>\\d+) столбец, (?<stashY>\\d+) ряд\\)\\.?(?<bonusText>.+)?",
            "Здравствуйте, хочу купить у вас ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) за ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) в лиге (?<league>.+)\\.?(?<bonusText>.+)?",
            "Здравствуйте, хочу купить у вас (?<itemName>.+) в лиге (?<league>.+) \\(секция \\\\?\"(?<stashtabName>.+)\"; позиция: (?<stashX>\\d+) столбец, (?<stashY>\\d+) ряд\\)\\.?(?<bonusText>.+)?",
            "Здравствуйте, хочу купить у вас (?<itemName>.+) за ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) в лиге (?<league>.+)\\.?(?<bonusText>.+)?",
            "Здравствуйте, хочу купить у вас (?<itemName>.+) в лиге (?<league>.+)\\.?(?<bonusText>.+)?",
    }),
    SPANISH("comprar tu", "se unió al área\\.", null,
            "para", "de", "El modo No Molestar está deshabilitado", "El modo No Molestar está habilitado", new String[]{
            "Hola, quisiera comprar tu (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) en (?<league>.+) \\(pestaña de alijo \\\\?\"(?<stashtabName>.+)\"; posición: izquierda(?<stashX>\\d+), arriba (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "Hola, me gustaría comprar tu(s) ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) por mi ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) en (?<league>.+)\\.?(?<bonusText>.+)?",
            "Hola, quisiera comprar tu (?<itemName>.+) en (?<league>.+) \\(pestaña de alijo \\\\?\"(?<stashtabName>.+)\"; posición: izquierda(?<stashX>\\d+), arriba (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "Hola, quisiera comprar tu (?<itemName>.+) listado por ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) en (?<league>.+)\\.?(?<bonusText>.+)?",
            "Hola, quisiera comprar tu (?<itemName>.+) en (?<league>.+)\\.?(?<bonusText>.+)?",
    }),
    THAI("เราต้องการจะชื้อของคุณ", "เข้าสู่พื้นที่\\.", null,
            "ถึง", "จาก", "ปิดโหมด DND แล้ว", "เปิดโหมด DND แล้ว ตอบกลับอัตโนมัติ", new String[]{
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน ราคา ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) ใน (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; ตำแหน่ง: ซ้าย (?<stashX>\\d+), บน (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน (?<league>.+) \\(stash tab \\\\?\"(?<stashtabName>.+)\"; ตำแหน่ง: ซ้าย (?<stashX>\\d+), บน (?<stashY>\\d+)\\)\\.?(?<bonusText>.+)?",
            "สวัสดี เรามีความต้องการจะชื้อ  ((?<itemQuantity>\\d+((\\.|,)\\d+)?) (?<itemName>.+)) ของคุณ ฉันมี ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) ใน (?<league>.+)\\.?(?<bonusText>.+)?",
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน ราคา ((?<priceQuantity>\\d+((\\.|,)\\d+)?) (?<priceType>.+)) ใน (?<league>.+)\\.?(?<bonusText>.+)?",
            "สวัสดี, เราต้องการจะชื้อของคุณ (?<itemName>.+) ใน (?<league>.+)\\.?(?<bonusText>.+)?",
    }),
    ;

    // Input
    public final String wantToBuy;
    public final String joinedArea;
    public final String enteredArea;
    public final String messageTo;
    public final String messageFrom;
    public final String dndOff;
    public final String dndOn;
    public final String[] messages;

    // Generated
//    public Pattern[] tradeOfferPatterns;
    public Pattern[] tradeOfferPatternsNew;
    public Pattern[] quickPastePatterns;
    public Pattern joinedAreaPattern;
    public Pattern enteredAreaPattern;
    //    public Pattern enteredAreaPatternNew;
    private static final Set<String> incomingTags = new HashSet<>();
    private static final Set<String> outgoingTags = new HashSet<>();

    static {
        compileAll();
        // Support for chinese messages
        incomingTags.add("來自");
        outgoingTags.add("向");
    }

    LangRegex(String wantToBuy, String joinedArea, String enteredArea, String messageTo, String messageFrom, String dndOff, String dndOn, String[] messages) {
        this.wantToBuy = wantToBuy;
        this.joinedArea = joinedArea;
        this.enteredArea = enteredArea;
        this.messageTo = messageTo;
        this.messageFrom = messageFrom;
        this.dndOff = dndOff;
        this.dndOn = dndOn;
        this.messages = messages;
    }

    public void compile() {
//        tradeOfferPatterns = new Pattern[messages.length];
        tradeOfferPatternsNew = new Pattern[messages.length];
        quickPastePatterns = new Pattern[messages.length];
        for (int i = 0; i < messages.length; i++) {
//            String s = References.REGEX_MESSAGE_PREFIX + messages[i] + References.REGEX_SUFFIX;
//            tradeOfferPatterns[i] = Pattern.compile(s);
            tradeOfferPatternsNew[i] = Pattern.compile(messages[i]);
            quickPastePatterns[i] = Pattern.compile(References.REGEX_QUICK_PASTE_PREFIX + messages[i] + References.REGEX_SUFFIX);
            joinedAreaPattern = Pattern.compile(References.REGEX_JOINED_AREA_PREFIX + joinedArea + References.REGEX_SUFFIX);
            enteredAreaPattern = Pattern.compile(References.REGEX_CLIENT_DATA + References.REGEX_ENTERED_AREA_PREFIX + enteredArea);
        }
    }

    public static TradeOfferType getMessageType(String toFrom) {
        toFrom = toFrom.toLowerCase();
        if (incomingTags.contains(toFrom)) return TradeOfferType.INCOMING_TRADE;
        if (outgoingTags.contains(toFrom)) return TradeOfferType.OUTGOING_TRADE;
        ZLogger.err("Invalid message type: " + toFrom);
        return TradeOfferType.UNKNOWN;
    }

    private static void compileAll() {
        for (LangRegex lang : LangRegex.values()) {
            lang.compile();
            incomingTags.add(lang.messageFrom);
            outgoingTags.add(lang.messageTo);
        }
    }

}
