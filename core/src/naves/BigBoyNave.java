package naves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class BigBoyNave extends AbstractNave{

	public BigBoyNave() {
		super(new Texture(Gdx.files.internal("img/defaultNaveSprites")), 0.08f, 0.125f);
	}

}
