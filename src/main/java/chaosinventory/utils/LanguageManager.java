package chaosinventory.utils;

import chaosinventory.config.ChaosConfig;

public class LanguageManager {
    public static String get(String key, Object... args) {
        String lang = ChaosConfig.getCurrentLanguage();
        String text = getTranslation(lang, key);
        return String.format(text, args);
    }

    private static String getTranslation(String lang, String key) {
        if (lang.equals("it_it")) {
            switch (key) {
                case "chaos.command.stats": return "§6§l\uD83D\uDCCA STATISTICHE CHAOS";
                case "chaos.command.level": return "§7Livello: §e%d §7(§f%d%%§7 al prossimo)";
                case "chaos.command.xp": return "§7XP totali: §e%d";
                case "chaos.command.events": return "§7Eventi subiti: §e%d";
                case "chaos.command.xp_next": return "§7XP al prossimo livello: §e%d";
                case "chaos.command.frequent": return "§7XP al prossimo livello: §e%d";
                case "chaos.command.rewards": return "§6§l\uD83C\uDF81 RICOMPENSE CHAOS";
                case "chaos.command.current_level": return "§7Livello attuale: §e%d";
                case "chaos.command.unlocked": return "§7§lSbloccate:";
                case "chaos.command.none": return "§8 Nessuna ancora";
                case "chaos.command.next": return "§7§lProssime ricompense:";
                case "chaos.command.reload": return "§a✅ Configurazione ricaricata!";
                case "chaos.command.timer_set": return "§a⏰ Timer impostato a §e%d§a secondi!";
                case "chaos.command.triggered": return "§a\uD83D\uDCA5 Evento §e%s§a scatenato!";
                case "chaos.command.not_found": return "§c❌ Evento §e%s§c non trovato!";
                case "chaos.command.enabled": return "§a\uD83C\uDF00 Chaos ATTIVATO!";
                case "chaos.command.disabled": return "§c\uD83C\uDF00 Chaos DISATTIVATO!";
                case "chaos.command.available": return "§e\uD83D\uDCCB Eventi disponibili: §a%d";
                case "chaos.command.use_trigger": return "§7Usa §e/chaos trigger <nome> §7per attivare un evento";
                case "chaos.language.changed": return "§aLingua cambiata in %s!";
                case "chaos.language.current": return "§7Lingua attuale: §e%s";
                default: return key;
            }
        }

        if (lang.equals("es_es")) {
            switch (key) {
                case "chaos.command.stats": return "§6§l\uD83D\uDCCA ESTADÍSTICAS CHAOS";
                case "chaos.command.level": return "§7Nivel: §e%d §7(§f%d%%§7 para el siguiente)";
                case "chaos.command.xp": return "§7XP total: §e%d";
                case "chaos.command.events": return "§7Eventos sufridos: §e%d";
                case "chaos.command.xp_next": return "§7XP para siguiente nivel: §e%d";
                case "chaos.command.frequent": return "§7Eventos más frecuentes:";
                case "chaos.command.rewards": return "§6§l\uD83C\uDF81 RECOMPENSAS CHAOS";
                case "chaos.command.current_level": return "§7Nivel actual: §e%d";
                case "chaos.command.unlocked": return "§7§lDesbloqueadas:";
                case "chaos.command.none": return "§8 Ninguna aún";
                case "chaos.command.next": return "§l§lPróximas recompensas:";
                case "chaos.command.reload": return "§a✅ Configuratión recargada!";
                case "chaos.command.timer_set": return "§a⏰ Temporizador ajustado a §e%d§a segundos!";
                case "chaos.command.triggered": return "§a\uD83D\uDCA5 Evento §e%s§a activado!";
                case "chaos.command.not_found": return "§c❌ Evento §e%s§c no encontrado!";
                case "chaos.command.enabled": return "§a\uD83C\uDF00 Chaos ACTIVADO!";
                case "chaos.command.disabled": return "§c\uD83C\uDF00 Chaos DESACTIVADO";
                case "chaos.command.available": return "§e\uD83D\uDCCB Eventos disponibles: §a%d";
                case "chaos.command.use_trigger": return "§7Usa §e/chaos trigger <nombre> §7para activar un evento";
                case "chaos.language.changed": return "§aIdioma cambiado a %s!";
                case "chaos.language.current": return "§7Idioma actual: §e%s";
                default: return key;
            }
        }

        if (lang.equals("fr_fr")) {
            switch (key) {
                case "chaos.command.stats": return "§6§l\uD83D\uDCCA STATISTIQUES CHAOS";
                case "chaos.command.level": return "§7Niveau: §e%d §7(§f%d%%§7 pour le suivant)";
                case "chaos.command.xp": return "§7XP total: §e%d";
                case "chaos.command.events": return "§7Événements subis: §e%d";
                case "chaos.command.xp_next": return "§7XP pour niveau suivant: §e%d";
                case "chaos.command.frequent": return "§7Événements les plus fréquents:";
                case "chaos.command.rewards": return "§6§l\uD83C\uDF81 RÉCOMPENSES CHAOS";
                case "chaos.command.current_level": return "§7Niveau actuel: §e%d";
                case "chaos.command.unlocked": return "§7§lDébloquées:";
                case "chaos.command.none": return "§8 Aucune encore";
                case "chaos.command.next": return "§7§lProchaines récompenses:";
                case "chaos.command.reload": return "§a✅ Configuration rechargée!";
                case "chaos.command.timer_set": return "§a⏰ Minuteur réglé sur §e%d§a secondes!";
                case "chaos.command.triggered:": return "§a\uD83D\uDCA5 Événement §e%s§a déclenché!";
                case "chaos.command.not_found": return "§c❌ Événement §e%s§c non trouvé!";
                case "chaos.command.enabled": return "§a\uD83C\uDF00 Chaos ACTIVÉ!";
                case "chaos.command.disabled": return "§c\uD83C\uDF00 Chaos DÉSACTIVÉ!";
                case "chaos.command.available": return "§e\uD83D\uDCCB Événements disponibles: §a%d";
                case "chaos.command.use_trigger": return "§7Utilise §e/chaos trigger <nom> §7pour activer un événement";
                case "chaos.language.changed": return "§aLangue changée en %s!";
                case "chaos.language.current": return "§7Langue actuelle: §e%s";
                default: return key;
            }
        }

        if (lang.equals("de_de")) {
            switch (key) {
                case "chaos.command.stats": return "§6§l📊 CHAOS STATISTIKEN";
                case "chaos.command.level": return "§7Level: §e%d §7(§f%d%%§7 zum nächsten)";
                case "chaos.command.xp": return "§7Gesamt XP: §e%d";
                case "chaos.command.events": return "§7Ereignisse überlebt: §e%d";
                case "chaos.command.xp_next": return "§7XP zum nächsten Level: §e%d";
                case "chaos.command.frequent": return "§7Häufigste Ereignisse:";
                case "chaos.command.rewards": return "§6§l🎁 CHAOS BELOHNUNGEN";
                case "chaos.command.current_level": return "§7Aktuelles Level: §e%d";
                case "chaos.command.unlocked": return "§7§lFreigeschaltet:";
                case "chaos.command.none": return "§8  Noch keine";
                case "chaos.command.next": return "§7§lNächste Belohnungen:";
                case "chaos.command.reload": return "§a✅ Konfiguration neu geladen!";
                case "chaos.command.timer_set": return "§a⏰ Timer auf §e%d§a Sekunden gesetzt!";
                case "chaos.command.triggered": return "§a💥 Ereignis §e%s§a ausgelöst!";
                case "chaos.command.not_found": return "§c❌ Ereignis §e%s§c nicht gefunden!";
                case "chaos.command.enabled": return "§a🌀 Chaos AKTIVIERT!";
                case "chaos.command.disabled": return "§c🌀 Chaos DEAKTIVIERT!";
                case "chaos.command.available": return "§e📋 Verfügbare Ereignisse: §a%d";
                case "chaos.command.use_trigger": return "§7Benutze §e/chaos trigger <name> §7um ein Ereignis auszulösen";
                case "chaos.language.changed": return "§aSprache geändert zu %s!";
                case "chaos.language.current": return "§7Aktuelle Sprache: §e%s";
                default: return key;
            }
        }

        switch (key) {
            case "chaos.command.stats": return "§6§l\uD83D\uDCCA CHAOS STATISTICS";
            case "chaos.command.level": return "§7Level: §e%d §7(§f%d%%§7 to next)";
            case "chaos.command.xp": return "§7Total XP: §e%d";
            case "chaos.command.events": return "§7Events survived: §e%d";
            case "chaos.command.xp_next": return "XP to next level: §e%d";
            case "chaos.command.frequent": return "§7Most frequent events:";
            case "chaos.command.rewards": return "§6§l\uD83C\uDF81 CHAOS REWARDS";
            case "chaos.command.current_level": return "§7Current level: §e%d";
            case "chaos.command.unlocked": return "§7§lUnlocked:";
            case "chaos.command.none": return "§8 None yet";
            case "chaos.command.next": return "§7§lNext rewards:";
            case "chaos.command.reload": return "§a✅ Config reloaded!";
            case "chaos.command.timer_set": return "§a⏰ Timer set to §e%d§a seconds!";
            case "chaos.command.triggered": return "§a\uD83D\uDCA5 Event §e%s§a triggered!";
            case "chaos.command.not_found": return "§c❌ Event §e%s§c not found";
            case "chaos.command.enabled": return "§a\uD83C\uDF00 Chaos ENABLED!";
            case "chaos.command.disabled": return "§c\uD83C\uDF00 Chaos DISABLED!";
            case "chaos.command.available": return "§7Use §e/chaos trigger <name> §7 to activate an event";
            case "chaos.language.changed": return "§aLanguage changed to %s!";
            case "chaos.language.current": return "§7Current language: §e%s";
            default: return key;
        }
    }
}
