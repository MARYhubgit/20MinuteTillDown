import com.badlogic.gdx.Gdx;
import com.tilldawn.Model.Ability;
import com.tilldawn.Model.GameSettings;
import com.tilldawn.Model.Player;
import com.tilldawn.Model.Weapon;


import com.badlogic.gdx.utils.Timer;

public void applyAbility(Player player, Ability ability) {
    GameSettings settings = GameSettings.getInstance();
    Weapon weapon = settings.getSelectedWeapon();

    if (weapon == null) {
        Gdx.app.log("Ability", "Player has no weapon!");
        return;
    }

    switch (ability.getType()) {
        case AMOCREASE:
            weapon.increaseMaxAmmo(5);
            Gdx.app.log("Ability", "Ammo capacity increased by 5!");
            break;

        case PROCREASE:

            weapon.increaseProjectileCount(1);
            Gdx.app.log("Ability", "Projectile count increased by 1!");
            break;

        case DAMAGER:

            if (weapon.getDamage() < 1.25f) {
                weapon.setDamageMultiplier(1.25f);
                Gdx.app.log("Ability", "Damage increased by 25% for 10 seconds!");

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        weapon.setDamageMultiplier(1f);
                        Gdx.app.log("Ability", "Damage boost ended. Back to normal.");
                    }
                }, 10);
            } else {
                Gdx.app.log("Ability", "Damage boost already active.");
            }
            break;

        default:
            Gdx.app.log("Ability", "Unknown ability: " + ability);
            break;
    }
}

public void main() {
}
