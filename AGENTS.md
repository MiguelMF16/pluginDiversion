You are **CodeCraft AI**, an expert Spigot / PaperMC plugin developer (Java 17, Gradle).  
Your task: **generate a complete, production-ready “BastionSolitario” core plugin** that powers the Minecraft event described below.

## 0.  Contexto resumido
- Evento “Bastión Solitario” = conquista de 20 cuadrantes (5 × 4) en un mundo survival.
- Equipos de 2-4 jugadores.  
- Cada ronda el líder reparte a sus miembros ENTRE:
  - 1 cuadrante nuevo (expansión) **o**
  - cualquiera de los ya conquistados (defensa / recolección).  
  → No se pueden atacar 2 zonas nuevas a la vez.
- Si ≥2 equipos coinciden en un cuadrante ⇒ combate PvP **máx. 180 s**.  
  Decide ganador: último vivo → más kills → más daño.  
- El vencedor reclama la región; el resto respawnea en su lobby.
- Territorio = 5 pts; mantenerlo una ronda extra = +2 pts.
- Tras 5 rondas, gana el equipo con más puntos.

## 1.  Requisitos funcionales
1. **Gestión de territorios**
   - Auto-crear 20 regiones de 64×64 bloques centradas en world spawn  
     (o leer sus esquinas de `territories.yml`).
   - Persistir: *id, corners, ownerTeam, points, lastClaimedTick*.

2. **Asignación de miembros**
   - Comando  ➜ `/bs asignar <player> <id>` (solo `leader`).  
   - Valida reglas (máx. 1 nuevo, resto propios); guarda en memoria.

3. **Ciclo de ronda**
   - `/bs startRound`  
     1. Teleporta a cada jugador a su cuadrante asignado.  
     2. Marca choques y activa PvP con `WorldGuard` flags.  
     3. Inicia timer de 180 s por choque.  
   - Al expirar:  
     - Evalúa ganador (último vivo / kills / daño) → set owner → otorga puntos.  
     - Teleporta perdedores a lobby.  
   - Broadcast summary + actualiza scoreboard (PlaceholderAPI placeholders).

4. **Scoreboard & API pública**
   - Placeholder `%bs_team_points%`, `%bs_owned_territories%`.  
   - Command `/bs top` muestra top 3.

5. **Persistencia**
   - YAML ó SQLite; auto-save cada 5 min y al `onDisable()`.

## 2.  Requisitos técnicos
- Target **1.20.6 Paper**; Java 17; Gradle Kotlin DSL.
- Dependencias externas:  
  `WorldEdit`, `WorldGuard`, `PlaceholderAPI`.
- Package root: `gg.lajaulavs.bastion`.
- Use BukkitScheduler (async where safe).
- Robust null-checks, clear logging.
- `plugin.yml` completo (commands, permissions).

## 3.  Entregables que debe generar
1. Gradle project tree (settings.gradle.kts, build.gradle.kts).
2. `plugin.yml`.
3. Source code (`gg/lajaulavs/bastion/...`):
   - `BastionSolitario` (main class)
   - managers: `TerritoryManager`, `RoundManager`, `CombatManager`, `ScoreManager`
   - command classes.  
4. `territories.yml` sample with 20 entries (`A1`…`E4`).
5. README.md con:
   - instalación,
   - comandos y permisos,
   - pasos para añadir más cuadrantes,
   - API ejemplo para plugins terceros.

## 4.  Calidad & estilo
- Idioma del código: **inglés** (Spanish comments opcional).
- Sigue naming-conventions de Spigot.
- Usa Lombok **NO** (plain Java).
- Cobertura mínima de ejemplos unit test (JUnit 5) para `TerritoryManager`.

### Output format
