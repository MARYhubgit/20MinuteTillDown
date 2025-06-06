package com.tilldawn.Model;

public enum WeaponType {
    SMG_DUAL(24, 2f, 1, 8),
    REVOLVER(6, 1f, 1, 20),
    SHOTGUN(2, 1f, 4, 10);

    private int maxAmmo;
    private float reloadTime;
    private int fireRate;
    private  int damage;

    WeaponType(int maxAmmo, float reloadTime, int projectiles, int damage) {
        this.maxAmmo = maxAmmo;
        this.reloadTime = reloadTime;
        this.fireRate = projectiles;
        this.damage = damage;
    }

    public int getMaxAmmo() { return maxAmmo; }
    public float getReloadTime() { return reloadTime; }
    public int getfireRate() { return fireRate; }
    public int getDamage() { return damage; }
    public void setMaxAmmo(int maxAmmo) { this.maxAmmo = maxAmmo; }
    public void setFireRate (int fireRate) { this.fireRate = fireRate; }
    public void setDamage(int damage) { this.damage = damage; }
}
