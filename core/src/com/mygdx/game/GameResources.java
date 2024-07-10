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
    public static final Texture DIAMOND_PICKAXE = new Texture("textures/items/pickaxes/diamond_pickaxe.png");
    public static final Texture GOLD_PICKAXE = new Texture("textures/items/pickaxes/gold_pickaxe.png");
    public static final Sprite IRON_PICKAXE = new Sprite(new Texture("textures/items/pickaxes/iron_pickaxe.png"));
    public static final Sprite STICK_PICKAXE = new Sprite(new Texture("textures/items/pickaxes/stick.png"));
    public static final Sprite STONE_PICKAXE = new Sprite(new Texture("textures/items/pickaxes/stone_pickaxe.png"));
    public static final Sprite[] RIGHT_STONE_PICKAXE = new Sprite[] {
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig1.png")),
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig2.png")),
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig3.png")),
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig4.png")),
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig5.png")),
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig6.png")),
         new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stone/stone_dig7.png"))
    };
    public static final Sprite[] RIGHT_STICK_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig6.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/stick/stick_dig7.png"))
    };
    public static final Sprite[] RIGHT_IRON_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig6.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/iron/iron_dig7.png"))
    };
    public static final Sprite[] RIGHT_GOLD_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig6.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/gold/gold_dig7.png"))
    };
    public static final Sprite[] RIGHT_DIAMOND_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig6.png")),
            new Sprite(new Texture("textures/hero/heroDigging/rightDigging/pickaxes/diamond/diamond_dig7.png"))
    };


    public static final Sprite[] DOWN_STONE_PICKAXE = new Sprite[] {
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stone/stone_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stone/stone_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stone/stone_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stone/stone_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stone/stone_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stone/stone_dig6.png"))
    };
    public static final Sprite[] DOWN_STICK_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stick/stick_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stick/stick_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stick/stick_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stick/stick_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stick/stick_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/stick/stick_dig6.png"))
    };
    public static final Sprite[] DOWN_IRON_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/iron/iron_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/iron/iron_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/iron/iron_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/iron/iron_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/iron/iron_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/iron/iron_dig6.png"))
    };
    public static final Sprite[] DOWN_GOLD_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/gold/gold_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/gold/gold_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/gold/gold_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/gold/gold_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/gold/gold_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/gold/gold_dig6.png"))
    };
    public static final Sprite[] DOWN_DIAMOND_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/diamond/diamond_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/diamond/diamond_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/diamond/diamond_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/diamond/diamond_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/diamond/diamond_dig5.png")),
            new Sprite(new Texture("textures/hero/heroDigging/downDigging/pickaxes/diamond/diamond_dig6.png"))
    };

    public static final Sprite[] UP_STONE_PICKAXE = new Sprite[] {
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stone/stone_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stone/stone_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stone/stone_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stone/stone_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stone/stone_dig5.png"))
    };
    public static final Sprite[] UP_STICK_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stick/stick_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stick/stick_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stick/stick_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stick/stick_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/stick/stick_dig5.png"))
    };
    public static final Sprite[] UP_IRON_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/iron/iron_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/iron/iron_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/iron/iron_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/iron/iron_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/iron/iron_dig5.png"))
    };
    public static final Sprite[] UP_GOLD_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/gold/gold_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/gold/gold_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/gold/gold_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/gold/gold_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/gold/gold_dig5.png"))
    };
    public static final Sprite[] UP_DIAMOND_PICKAXE = new Sprite[]{
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/diamond/diamond_dig1.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/diamond/diamond_dig2.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/diamond/diamond_dig3.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/diamond/diamond_dig4.png")),
            new Sprite(new Texture("textures/hero/heroDigging/upDigging/pickaxes/diamond/diamond_dig5.png"))
    };


    //BLOCKS
    public static final String DIRT_BLOCK_TEXTURE = "textures/blocks/up/dirt.png";
    public static final String STONE_BLOCK_TEXTURE = "textures/blocks/stone/stone.png";
    public static final String GRASS_BLOCK_TEXTURE = "textures/blocks/up/grass.png";
    public static final String AMETHYST_BLOCK_TEXTURE = "textures/blocks/stone/amethystblock.png";
    public static final String COAL_BLOCK_TEXTURE = "textures/blocks/stone/coalblock.png";
    public static final String DIAMOND_BLOCK_TEXTURE = "textures/blocks/stone/diamondblock.png";
    public static final String EMERALD_BLOCK_TEXTURE = "textures/blocks/stone/emelardblock.png";
    public static final String GOLD_BLOCK_TEXTURE = "textures/blocks/stone/goldblock.png";
    public static final String IRON_BLOCK_TEXTURE = "textures/blocks/stone/ironblock.png";
    public static final String LAZURITE_BLOCK_TEXTURE = "textures/blocks/stone/lazuriteblock.png";
    public static final String MOSSY_BLOCK_TEXTURE = "textures/blocks/stone/mossyblock.png";
    public static final String RUBY_BLOCK_TEXTURE = "textures/blocks/stone/rubyblock.png";

    public static final String FOOD_MARKET_TEXTURE = "textures/buildings/foodshop.png";
    public static final String SELL_MARKET_TEXTURE = "textures/buildings/market.png";
    public static final String UPDATE_MARKET_TEXTURE = "textures/buildings/updateshop.png";

    public static final String LEAVES_TEXTURE = "textures/blocks/up/leaves.png";
    public static final String WOOD_BLOCK_TEXTURE = "textures/blocks/up/wood.png";
    public static final String WOOD2_BLOCK_TEXTURE = "textures/blocks/up/wood2.png";

    public static final String FIRE_OVEN_TEXTURE = "textures/blocks/ovens/fireoven.png";
    public static final String OVEN_TEXTURE = "textures/blocks/ovens/oven.png";

    public static final String DIAMOND_PICKAXE_TEXTURE = "textures/items/pickaxes/diamond_pickaxe.png";
    public static final String GOLD_PICKAXE_TEXTURE = "textures/items/pickaxes/gold_pickaxe.png";
    public static final String IRON_PICKAXE_TEXTURE = "textures/items/pickaxes/iron_pickaxe.png";
    public static final String STICK_PICKAXE_TEXTURE = "textures/items/pickaxes/stick.png";
    public static final String STONE_PICKAXE_TEXTURE = "textures/items/pickaxes/stone_pickaxe.png";

    //UI
    public static final Texture JOYSTICK_BACKGROUND_TEXTURE = new Texture("textures/joystick/joystick.png");
    public static final Sprite JOYSTICK_TRIGGER_TEXTURE = new Sprite(new Texture("textures/joystick/joystickTrigger.png"));
    public static final Texture BACKPACK_BUTTON_IMG = new Texture("textures/buttons/main_screen/bag_button_off.png");
    public static final Texture BUTTON_BACKGROUND = new Texture("textures/buttons/bag_screen/block_button.png");
    public static final Texture EXIT_BUTTON = new Texture("textures/buttons/exit_button.png");
    public static final Texture MONEY = new Texture("textures/items/money.png");
    public static final Texture STONE_BLOCK_BACKGROUND = new Texture("textures/backscreens/stone_block_backscreen.png");
    public static final Texture SELECTED_BLOCK = new Texture("textures/blocks/selected_block.png");

    public static final String SKY = "textures/backscreens/sky.png";
    public static final Texture JUMP_BUTTON = new Texture("textures/buttons/main_screen/jump_button_off.png");

    public static final Texture  ACTION_BUTTON =  new Texture("textures/buttons/main_screen/action_button.png");


    //npc
    public static final Sprite FOODSELLER_HEAD_TEXTURE = new Sprite(new Texture("textures/npc/food_seller_head.png"));

    public static final Texture MENU_BUTTON = new Texture("textures/buttons/pause_menu_settings_screen/button.png");
    public static final String MENU_SCREEN = "textures/backscreens/menu_screen.png";



}
