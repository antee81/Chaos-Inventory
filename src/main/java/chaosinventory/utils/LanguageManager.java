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

                case "chaos.leaderboard.title": return "§6§l\uD83C\uDFC6 CLASSIFICA";
                case "chaos.leaderboard.empty": return "§7Nessun giocatore trovato!";
                case "chaos.leaderboard.your_position": return "§7La tua posizione:";
                case "chaos.leaderboard.xp": return "XP: §e%d";
                case "chaos.leaderboard.level": return "Livello: §a%d";
                case "chaos.leaderboard.coins": return "Monete: §6%d";
                case "chaos.leaderboard.events": return "Eventi: §c%d";
                case "chaos.leaderboard.separator": return "§7━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";
                case "chaos.leaderboard.rank": return "#%d";

                case "chaos.shop.title": return "§6§lNEGOZIO CHAOS";
                case "chaos.shop.click_to_buy": return "§7Clicca per comprare";
                case "chaos.shop.bought": return "§a✅ Hai comprato §e%s§a per §6%d monete§a!";
                case "chaos.shop.not_enough": return "§c❌ Ti servono §e%d§c monete in più!";

                case "chaos.achievement.unlocked": return "§6§l\uD83C\uDFC6 OBIETTIVO SBLOCCO! §r§6%s\n§7%s";

                case "chaos.afk.paused": return "§c⏸\uFE0F Sei AFK! Il timer riprenderà quando ti muovi.";
                case "chaos.afk.resumed": return "§a✅ Non sei più AFK! Timer chaos ripreso.";

                case "chaos.coins.gained": return "§6\uD83D\uDCB0 +%d Chaos Coins! §7Totale: §e%d";
                case "chaos.coins.spent": return "§6\uD83D\uDCB0 -%d Chaos Coins! §7Rimaste: §e%d";
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

                case "chaos.leaderboard.title": return "§6§l🏆 CLASIFICACIÓN";
                case "chaos.leaderboard.empty": return "§7¡No se encontraron jugadores!";
                case "chaos.leaderboard.your_position": return "§7Tu posición:";
                case "chaos.leaderboard.xp": return "XP: §e%d";
                case "chaos.leaderboard.level": return "Nivel: §a%d";
                case "chaos.leaderboard.coins": return "Monedas: §6%d";
                case "chaos.leaderboard.events": return "Eventos: §c%d";
                case "chaos.leaderboard.rank": return "#%d";

                case "chaos.shop.title": return "§6§lTIENDA CHAOS";
                case "chaos.shop.click_to_buy": return "§7Clic para comprar";
                case "chaos.shop.bought": return "§a✅ Compraste §e%s§a por §6%d monedas§a!";
                case "chaos.shop.not_enough": return "§c❌ Necesitas §e%d§c monedas más";

                case "chaos.achievement.unlocked": return "§6§l🏆 LOGRO DESBLOQUEADO! §r§6%s\n§7%s";

                case "chaos.afk.paused": return "§c⏸️ ¡Estás AFK! El temporizador se reanudará cuando te muevas.";
                case "chaos.afk.resumed": return "§a✅ Ya no estás AFK! Temporizador Chaos reanudado.";

                case "chaos.coins.gained": return "§6💰 +%d Monedas Chaos! §7Total: §e%d";
                case "chaos.coins.spent": return "§6💰 -%d Monedas Chaos! §7Restantes: §e%d";
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

                case "chaos.leaderboard.title": return "§6§l🏆 CLASSEMENT";
                case "chaos.leaderboard.empty": return "§7Aucun joueur trouvé !";
                case "chaos.leaderboard.your_position": return "§7Ta position :";
                case "chaos.leaderboard.xp": return "XP : §e%d";
                case "chaos.leaderboard.level": return "Niveau : §a%d";
                case "chaos.leaderboard.coins": return "Pièces : §6%d";
                case "chaos.leaderboard.events": return "Événements : §c%d";
                case "chaos.leaderboard.rank": return "#%d";

                case "chaos.shop.title": return "§6§lBOUTIQUE CHAOS";
                case "chaos.shop.click_to_buy": return "§7Cliquez pour acheter";
                case "chaos.shop.bought": return "§a✅ Tu as acheté §e%s§a pour §6%d pièces§a !";
                case "chaos.shop.not_enough": return "§c❌ Il te faut §e%d§c pièces de plus";

                case "chaos.achievement.unlocked": return "§6§l🏆 SUCCÈS DÉBLOQUÉ ! §r§6%s\n§7%s";

                case "chaos.afk.paused": return "§c⏸️ Tu es AFK ! La minuterie reprendra quand tu bougeras.";
                case "chaos.afk.resumed": return "§a✅ Tu n'es plus AFK ! Minuterie Chaos reprise.";

                case "chaos.coins.gained": return "§6💰 +%d Pièces Chaos ! §7Total : §e%d";
                case "chaos.coins.spent": return "§6💰 -%d Pièces Chaos ! §7Restant : §e%d";
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

                case "chaos.leaderboard.title": return "§6§l🏆 BESTENLISTE";
                case "chaos.leaderboard.empty": return "§7Keine Spieler gefunden!";
                case "chaos.leaderboard.your_position": return "§7Deine Position:";
                case "chaos.leaderboard.xp": return "XP: §e%d";
                case "chaos.leaderboard.level": return "Level: §a%d";
                case "chaos.leaderboard.coins": return "Münzen: §6%d";
                case "chaos.leaderboard.events": return "Ereignisse: §c%d";
                case "chaos.leaderboard.rank": return "#%d";

                case "chaos.shop.title": return "§6§lCHAOS LADEN";
                case "chaos.shop.click_to_buy": return "§7Zum Kaufen klicken";
                case "chaos.shop.bought": return "§a✅ Du hast §e%s§a für §6%d Münzen§a gekauft!";
                case "chaos.shop.not_enough": return "§c❌ Du benötigst §e%d§c Münzen mehr";

                case "chaos.achievement.unlocked": return "§6§l🏆 ERFOLG FREIGESCHALTET! §r§6%s\n§7%s";

                case "chaos.afk.paused": return "§c⏸️ Du bist AFK! Der Timer wird fortgesetzt, wenn du dich bewegst.";
                case "chaos.afk.resumed": return "§a✅ Du bist nicht mehr AFK! Chaos Timer fortgesetzt.";

                case "chaos.coins.gained": return "§6💰 +%d Chaos Münzen! §7Gesamt: §e%d";
                case "chaos.coins.spent": return "§6💰 -%d Chaos Münzen! §7Verbleibend: §e%d";
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

            case "chaos.leaderboard.title": return "§6§l\uD83C\uDFC6 LEADERBOARD";
            case "chaos.leaderboard.empty": return "§7No players found!";
            case "chaos.leaderboard.your_position": return "§7Your position:";
            case "chaos.leaderboard.xp": return "XP: §e%d";
            case "chaos.leaderboard.level": return "Level: §a%d";
            case "chaos.leaderboard.coins": return "Coins: §6%d";
            case "chaos.leaderboard.events": return "Events: §c%d";
            case "chaos.leaderboard.rank": return "#%d";

            case "chaos.shop.title": return "§6§lCHAOS SHOP";
            case "chaos.shop.click_to_buy": return "§7Click to buy";
            case "chaos.shop.bought": return "§a✅ You bought §e%s§a for §6%d coins§a!";
            case "chaos.shop.not_enough": return "§c❌ You need §e%d§c more coins";

            case "chaos.achievement.unlocked": return "§6§l\uD83C\uDFC6 ACHIEVEMENT UNLOCKED! §r§6%s\n§7%s";

            case "chaos.afk.paused": return "§c⏸\uFE0F You're AFK! Timer will resume when you start moving.";
            case "chaos.afk.resumed": return "§a✅ You are no longer AFK! Chaos Timer resumed.";

            case "chaos.coins.gained": return "§6\uD83D\uDCB0 +%d Chaos Coins! §7Total: §e%d";
            case "chaos.coins.spent": return "§6\uD83D\uDCB0 -%d Chaos Coins! §7Remaining: §e%d";
            default: return key;
        }
    }
}
