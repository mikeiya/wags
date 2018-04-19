package wags.gravatar;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileCacher {
    private File cacherDir;

    public FileCacher(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacherDir = new File(Environment.getExternalStorageDirectory(), "fcImages");
        } else {
            cacherDir = context.getCacheDir();
        }
        if (!cacherDir.mkdirs()) {
            cacherDir.mkdirs();
        }
    }

    public File getFile(String url) {
        String Filename = String.valueOf(url.hashCode());
        File f = new File(cacherDir, Filename);
        return f;
    }

    public void clear() {
        File[] files = cacherDir.listFiles();
        if (files == null) {
            return;
        }
        for (File f : files) {
            f.delete();
        }
    }
}
