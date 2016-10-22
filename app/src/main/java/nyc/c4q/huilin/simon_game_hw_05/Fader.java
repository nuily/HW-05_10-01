package nyc.c4q.huilin.simon_game_hw_05;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by huilin on 10/21/16.
 */

public class Fader {

    public static void RunAlphaAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.alpha);
        if (animation == null) {
            return;
        }
        animation.reset();
        if (view != null) {
            view.clearAnimation();
            view.startAnimation(animation);
        }

    }
}
