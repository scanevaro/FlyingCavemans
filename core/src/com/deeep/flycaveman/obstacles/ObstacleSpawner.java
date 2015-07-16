package com.deeep.flycaveman.obstacles;

import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.obstacles.types.Mosquito;
import com.deeep.flycaveman.obstacles.types.Plant;
import com.deeep.flycaveman.obstacles.types.Quetzalcoatlus;
import com.deeep.flycaveman.obstacles.types.Toucan;
import com.deeep.flycaveman.world.Biomes;
import com.deeep.flycaveman.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Elmar on 6-6-2015.
 */
public class ObstacleSpawner {

    private ArrayList<ObstacleBiome> obstacleBiomes = new ArrayList<ObstacleBiome>();

    public ObstacleSpawner() {
        obstacleBiomes.add(new ObstacleBiome(BiomeTypes.DESSERT, Biomes.DESSERT, Biomes.JUNGLE_DESSERT));
        obstacleBiomes.add(new ObstacleBiome(BiomeTypes.OCEAN, Biomes.OCEAN));
        obstacleBiomes.add(new ObstacleBiome(BiomeTypes.JUNGLE, Biomes.JUNGLE, Biomes.DESSERT_JUNGLE));
        obstacleBiomes.add(new ObstacleBiome(BiomeTypes.SPACE));
        registerObstacles();
    }

    private void registerObstacles() {
        registerObstacle(Toucan.class, 1f, true, BiomeTypes.ALL.numbers);
        registerObstacle(Quetzalcoatlus.class, 1f, true, BiomeTypes.DESSERT.numbers);
        registerObstacle(Plant.class, 1f, true, BiomeTypes.JUNGLE.numbers);
        registerObstacle(Mosquito.class, 1f, true, BiomeTypes.JUNGLE.numbers);
    }

    private void registerObstacle(Class<? extends ObstacleBase> obstacle, float chance, boolean flying, int... biomes) {
        for (ObstacleBiome obstacleBiome : obstacleBiomes) {
            for (int biome : biomes) {
                if (biome == -1 || obstacleBiome.isType(biome)) {
                    obstacleBiome.registerObstacle(obstacle, chance, flying);
                }
            }
        }
    }

    public void spawn(World world, Body caveman) {
        int biomeType = 0;
        Random random = new Random();
        for (ObstacleBiome obstacleBiome : obstacleBiomes) {
            if (obstacleBiome.isType(biomeType)) {
                obstacleBiome.spawn(world, caveman);
                //spawn
                break;
            }
        }
    }

    private class ObstacleBiome {
        private int[] biomes;
        public float totalChance;
        private BiomeTypes type; // for reference
        private ArrayList<SpawnAbleObstacle> spawnAbleObstacles = new ArrayList<SpawnAbleObstacle>();

        public ObstacleBiome(BiomeTypes type, int... biome) {
            this.type = type;
            biomes = biome;
        }

        public void registerObstacle(Class<? extends ObstacleBase> obstacle, float chance, boolean flying) {
            SpawnAbleObstacle spawnAbleObstacle = new SpawnAbleObstacle();
            spawnAbleObstacle.chance = chance;
            spawnAbleObstacle.flying = flying;
            spawnAbleObstacle.obstacle = obstacle;
            spawnAbleObstacles.add(spawnAbleObstacle);
            totalChance += chance;
        }

        public void spawn(World world, Body caveman) {
        }

        public boolean isType(int biome) {
            for (int temp : biomes) {
                if (temp == biome)
                    return true;
            }
            return false;
        }

        public void spawnObstacle(World world, CaveMan caveMan) {
            Class<? extends ObstacleBase> obstacleBaseClass = null;
            float tempChance = totalChance;
            for(int i = 0, l = spawnAbleObstacles.size(); i<l; i++){
                tempChance -= spawnAbleObstacles.get(i).chance;
                if (tempChance <= 0) {
                    obstacleBaseClass = spawnAbleObstacles.get(i).obstacle;
                    break;
                }
            }
            try {
                Constructor<?> constructor = obstacleBaseClass.getConstructor(World.class);
                ObstacleBase obstacleBase = (ObstacleBase) constructor.newInstance(world);
                obstacleBase.setPosition(3,3);
            } catch (NoSuchMethodException e) {
                //something goes terribly wrong
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        private class SpawnAbleObstacle {
            boolean flying;
            float chance;
            Class<? extends ObstacleBase> obstacle;
        }

    }
}
