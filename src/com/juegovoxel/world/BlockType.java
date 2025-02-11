package com.juegovoxel.world;

public enum BlockType {
    DIRT("dirt", "dirt", "dirt"),
    GRASS("grass_top", "dirt", "grass_side");

    private String topTexture;
    private String bottomTexture;
    private String sideTexture;

    BlockType(String topTexture, String bottomTexture, String sideTexture) {
        this.topTexture = topTexture;
        this.bottomTexture = bottomTexture;
        this.sideTexture = sideTexture;
    }

    public String getTopTexture() {
        return topTexture;
    }

    public String getBottomTexture() {
        return bottomTexture;
    }

    public String getSideTexture() {
        return sideTexture;
    }
}
      