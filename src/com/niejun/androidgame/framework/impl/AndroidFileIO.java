package com.niejun.androidgame.framework.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.niejun.androidgame.framework.FileIO;

public class AndroidFileIO implements FileIO {

	Context context;
	AssetManager assets;
	String externalStoragePath;

	public AndroidFileIO(Context context) {
		super();
		this.context = context;
		this.assets = context.getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
	}

	@Override
	public InputStream readAsset(String fileName) throws IOException {
		return this.assets.open(fileName);
	}

	@Override
	public InputStream readFile(String fileName) throws IOException {
		return new FileInputStream(this.externalStoragePath + fileName);
	}

	@Override
	public OutputStream writeFile(String fileName) throws IOException {
		return new FileOutputStream(this.externalStoragePath + fileName);
	}

	public SharedPreferences getPreferences(){
		return PreferenceManager.getDefaultSharedPreferences(this.context);
	}
}
