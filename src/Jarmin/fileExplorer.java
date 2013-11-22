package Jarmin;

import java.io.File;

public class fileExplorer { 

	String[] songs = new String[50];
	String[] albums = new String[50];
	String[] artists = new String[50];
	String[] library = new String[150];
	int indexSongs = 0;
	int indexAlbums = 0;
	int indexArtists = 0;
	int indexLibrary = 0;
	int level =0;

	public void fileWalker(String pathname, int lv) throws InterruptedException {

		File f = new File(pathname);
		File[] listfiles = f.listFiles();

		if(listfiles != null){
			for (int i = 0; i < listfiles.length; i++) {
				if (listfiles[i].isDirectory()) {
					artists[indexArtists] = listfiles[i].getName();
					indexArtists++;
					;
					level = lv;

					File[] internalFile = listfiles[i].listFiles();
					int j=0;
					for (j = 0; j < internalFile.length; j++) {

						if(level==0){

							albums[indexAlbums] = internalFile[j].getName();
							indexAlbums++;

						}
						if (internalFile[j].isDirectory()) {
							String name = internalFile[j].getAbsolutePath();
							fileWalker(name,level);
						}
					}
				} else {
					Thread.sleep(100);

					songs[indexSongs] = listfiles[i].getName();
					indexSongs++;
				}

			}
		}
		else 
			System.err.println("Folder is Empty!");
	}

	public String[] getSongs() {
		return this.songs;
	}

	public String[] getAlbums() {
		return this.albums;
	}

	public String[] getArtists() {
		return this.artists;
	}

}