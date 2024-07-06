package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class GameResources {
    //PLAYER STATES
    public static final Sprite[] PLAYER_WALKING_TEXTURES = new Sprite[]{
            new Sprite(new Texture("textures/hero/walking/step2.png")),
            new Sprite(new Texture("textures/hero/walking/step3.png")),
            new Sprite(new Texture("textures/hero/walking/step4.png")),
            new Sprite(new Texture("textures/hero/walking/step5.png")),
            new Sprite(new Texture("textures/hero/walking/step6.png")),
            new Sprite(new Texture("textures/hero/walking/step7.png")),
            new Sprite(new Texture("textures/hero/walking/step8.png")),
            new Sprite(new Texture("textures/hero/walking/step9.png"))
    };
    public static final Sprite[] BREAKING_BLOCKS = new Sprite[]{
            new Sprite(new Texture("textures/blocks/digging/block_digging1.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging2.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging3.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging4.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging5.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging6.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging7.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging8.png"))
    };
    public static final Sprite[] PLAYER_DOWN_DIGGING_TEXTURES = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/down_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/down_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/down_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/down_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/down_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/down_dig6.png"))
    };
    public static final Sprite[] PLAYER_SIDE_DIGGING_TEXTURES = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig6.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/dig7.png"))
    };
    public static final Sprite[] PLAYER_UP_DIGGING_TEXTURES = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/up_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/up_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/up_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/up_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/up_dig5.png"))
    };
    public static final Sprite[] PLAYER_JUMPING_TEXTURES = new Sprite[]{
            new Sprite(new Texture("textures/hero/jumping/jump1.png")),
            new Sprite(new Texture("textures/hero/jumping/jump2.png")),
            new Sprite(new Texture("textures/hero/jumping/jump3.png")),
            new Sprite(new Texture("textures/hero/jumping/jump4.png")),
            new Sprite(new Texture("textures/hero/jumping/jump5.png")),
    };

    public static final Sprite PLAYER_HEAD_TEXTURE = new Sprite(new Texture("textures/hero/walking/head.png"));
    public static final Sprite PLAYER_STANDING_TEXTURE = new Sprite(new Texture("textures/hero/walking/step1.png"));

    //ITEMS
    public static final Sprite DIAMOND_PICKAXE = new Sprite(new Texture("textures/items/diamond_pickaxe.png"));
    public static final Sprite GOLD_PICKAXE = new Sprite(new Texture("textures/items/gold_pickaxe.png"));
    public static final Sprite IRON_PICKAXE = new Sprite(new Texture("textures/items/iron_pickaxe.png"));
    public static final Sprite STICK_PICKAXE = new Sprite(new Texture("textures/items/stick.png"));
    public static final Sprite STONE_PICKAXE = new Sprite(new Texture("textures/items/stone_pickaxe.png"));


    //BLOCKS
    public static final String DIRT_BLOCK_TEXTURE = "textures/blocks/dirt.png";
    public static final String STONE_BLOCK_TEXTURE = "textures/blocks/stone.png";
    public static final String GRASS_BLOCK_TEXTURE = "textures/blocks/grass.png";
    public static final String AMETHYST_BLOCK_TEXTURE = "textures/blocks/amethystblock.png";
    public static final String COAL_BLOCK_TEXTURE = "textures/blocks/coalblock.png";
    public static final String DIAMOND_BLOCK_TEXTURE = "textures/blocks/diamondblock.png";
    public static final String EMERALD_BLOCK_TEXTURE = "textures/blocks/emelardblock.png";
    public static final String GOLD_BLOCK_TEXTURE = "textures/blocks/goldblock.png";
    public static final String IRON_BLOCK_TEXTURE = "textures/blocks/ironblock.png";
    public static final String LAZURITE_BLOCK_TEXTURE = "textures/blocks/lazuriteblock.png";

    public static final String MOSSY_BLOCK_TEXTURE = "textures/blocks/mossyblock.png";
    public static final String RUBY_BLOCK_TEXTURE = "textures/blocks/rubyblock.png";

    public static final String LEAVES_TEXTURE = "textures/blocks/leaves.png";
    public static final String WOOD_BLOCK_TEXTURE = "textures/blocks/wood.png";
    public static final String WOOD2_BLOCK_TEXTURE = "textures/blocks/wood2.png";

    public static final String FIRE_OVEN_TEXTURE = "textures/blocks/fireoven.png";
    public static final String OVEN_TEXTURE = "textures/blocks/oven.png";

    public static final Sprite[] BLOCK_DIGGING_TEXTURES = new Sprite[]{
            new Sprite(new Texture("textures/blocks/digging/block_digging1.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging2.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging3.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging4.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging5.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging6.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging7.png")),
            new Sprite(new Texture("textures/blocks/digging/block_digging8.png"))
    };

    //UI
    public static final Sprite JOYSTICK_BACKGROUND_TEXTURE = new Sprite(new Texture("textures/joystick/joystick.png"));
    public static final Sprite JOYSTICK_TRIGGER_TEXTURE = new Sprite(new Texture("textures/joystick/joystickTrigger.png"));
    public static final String BACKPACK_BUTTON_IMG_PATH = "textures/buttons/button_background_square.png";


}
