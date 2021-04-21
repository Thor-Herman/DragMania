package com.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;

public class TextureScaler {
 
    private TextureScaler() {}

    public static Texture scale(Texture texture, double scale) {
        String texturePath = ((FileTextureData)texture.getTextureData()).getFileHandle().path();
        Pixmap oldPixMap = new Pixmap(Gdx.files.internal(texturePath));
        Pixmap newPixMap = new Pixmap((int) (oldPixMap.getWidth()*scale), (int) (oldPixMap.getHeight()*scale), oldPixMap.getFormat());
        newPixMap.drawPixmap(oldPixMap,
                0, 0, oldPixMap.getWidth(), oldPixMap.getHeight(),
                0, 0, newPixMap.getWidth(), newPixMap.getHeight()
        );
        Texture scaledTexture = new Texture(newPixMap);

        oldPixMap.dispose();
        newPixMap.dispose();

        return scaledTexture;
    }
    
    
}
