package com.decodinator.liroth.world.generator;

import java.util.HashSet;
import java.util.Set;

import com.decodinator.liroth.Liroth;
import com.decodinator.liroth.core.helpers.GenericJigsawStructureCodeConfig;

import net.fabricmc.fabric.api.structure.v1.FabricStructureBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class LirothStructures {

    public static final Set<Identifier> LIROTH_STRUCTURE_START_PIECES = new HashSet<>();
    /**
    /**
     * Registers the structure itself and sets what its path is. In this case, the
     * structure will have the Identifier of structure_tutorial:run_down_house.
     *
     * It is always a good idea to register your Structures so that other mods and datapacks can
     * use them too directly from the registries. It great for mod/datapacks compatibility.
     */
    public static StructureFeature<StructurePoolFeatureConfig> LIROTH_FORTRESS = new LirothFortressStructure(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> OLDEN_LIROTH_PORTAL = new OldenLirothPortalStructure(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> NOVA_TOWER = new NovaTowerStructure(StructurePoolFeatureConfig.CODEC);

    /**
     * This is where we use Fabric API's structure API to setup the StructureFeature
     * See the comments in below for more details.
     */
    public static void setupAndRegisterStructureFeatures() {

        // This is Fabric API's builder for structures.
        // It has many options to make sure your structure will spawn and work properly.
        // Give it your structure and the identifier you want for it.
        FabricStructureBuilder.create(new Identifier(Liroth.MOD_ID, "liroth_fortress"), LIROTH_FORTRESS)

                /* Generation stage for when to generate the structure. there are 10 stages you can pick from!
                   This surface structure stage places the structure before plants and ores are generated. */
                .step(GenerationStep.Feature.SURFACE_STRUCTURES)

                .defaultConfig(new StructureConfig(
                		27, /* average distance apart in chunks between spawn attempts */
                        4, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE */
                        30084232 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */))

                /*
                 * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
                 * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
                 * Doesn't work well on structure that have pieces stacked vertically or change in heights.
                 *
                 * Note: The air space this method will create will be filled with water if the structure is below sealevel.
                 * This means this is best for structure above sealevel so keep that in mind.
                 */

                /* Finally! Now we register our structure and everything above will take effect. */
                .register();

        FabricStructureBuilder.create(new Identifier(Liroth.MOD_ID, "olden_liroth_portal"), OLDEN_LIROTH_PORTAL)

        /* Generation stage for when to generate the structure. there are 10 stages you can pick from!
           This surface structure stage places the structure before plants and ores are generated. */
        .step(GenerationStep.Feature.SURFACE_STRUCTURES)

        .defaultConfig(new StructureConfig(
                50, /* average distance apart in chunks between spawn attempts */
                10, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE */
                269673572 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */))

        /*
         * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
         * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically or change in heights.
         *
         * Note: The air space this method will create will be filled with water if the structure is below sealevel.
         * This means this is best for structure above sealevel so keep that in mind.
         */
        .adjustsSurface()

        /* Finally! Now we register our structure and everything above will take effect. */
        .register();
        // Add more structures here and so on
        
        FabricStructureBuilder.create(new Identifier(Liroth.MOD_ID, "nova_tower"), NOVA_TOWER)

        /* Generation stage for when to generate the structure. there are 10 stages you can pick from!
           This surface structure stage places the structure before plants and ores are generated. */
        .step(GenerationStep.Feature.SURFACE_STRUCTURES)

        .defaultConfig(new StructureConfig(
        		30, /* average distance apart in chunks between spawn attempts */
                6, /* minimum distance apart in chunks between spawn attempts. MUST BE LESS THAN ABOVE VALUE */
                263555446 /* this modifies the seed of the structure so no two structures always spawn over each-other. Make this large and unique. */))

        /*
         * Whether surrounding land will be modified automatically to conform to the bottom of the structure.
         * Basically, it adds land at the base of the structure like it does for Villages and Outposts.
         * Doesn't work well on structure that have pieces stacked vertically or change in heights.
         *
         * Note: The air space this method will create will be filled with water if the structure is below sealevel.
         * This means this is best for structure above sealevel so keep that in mind.
         */
        .adjustsSurface()

        /* Finally! Now we register our structure and everything above will take effect. */
        .register();
    }
}