/*
 * Copyright (c) 2014. William Mora
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nicholaschirkevich.game.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.nicholaschirkevich.game.GameRuners;
import com.nicholaschirkevich.game.util.AssetsManager;
import com.nicholaschirkevich.game.util.Constants;


public class AchiveView extends Actor {

    private TextureRegion textureRegion;
    private Rectangle bounds;
    private static float width = 23, height = 12;

    public AchiveView(Rectangle bounds, String assetsId) {
        this.bounds = bounds;
        textureRegion = AssetsManager.getTextureRegion(assetsId);
        SequenceAction sequenceAction = new SequenceAction();
        sequenceAction.addAction(Actions.delay(1.5f));
        sequenceAction.addAction(Actions.removeActor());
        addAction(sequenceAction);
        setWidth(bounds.width);
        setHeight(bounds.height);
    }

    public static Actor getView(int gear) {
        switch (gear) {
            case 1:
                break;
            case 2:
                return new AchiveView(new Rectangle(GameRuners.WIDTH / 4 - (width / 2), GameRuners.HEIGHT / 4 - (height / 2), width, height), Constants.X2_id);

            case 3:
                return new AchiveView(new Rectangle(GameRuners.WIDTH / 4 - (width / 2), GameRuners.HEIGHT / 4 - (height / 2), width, height), Constants.X3_id);

            case 4:
                return new AchiveView(new Rectangle(GameRuners.WIDTH / 4 - (width / 2), GameRuners.HEIGHT / 4 - (height / 2), width, height), Constants.X4_id);

            case 5:
                return new AchiveView(new Rectangle(GameRuners.WIDTH / 4 - (width / 2), GameRuners.HEIGHT / 4 - (height / 2), width, height), Constants.X5_id);

            case 6:
                return new AchiveView(new Rectangle(GameRuners.WIDTH / 4 - (width / 2), GameRuners.HEIGHT / 4 - (height / 2), width, height), Constants.X6_id);

        }
        return null;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
//        if (GameManager.getInstance().getGameState() == GameState.OVER) {
//            remove();
//        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(textureRegion, bounds.x, bounds.y, bounds.width, bounds.height);

    }
}
