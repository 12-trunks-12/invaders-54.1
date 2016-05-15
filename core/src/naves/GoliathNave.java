package naves;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class GoliathNave extends AbstractNave{

	public GoliathNave() {
		super(new Texture(Gdx.files.internal("img/GoliathNaveSprites")), 0.08f, 0.125f);
	}

}
