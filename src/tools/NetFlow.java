package tools;
import java.net.*;
import java.io.*;


public class NetFlow {

	public String url ="http://o-o.preferred.ati-tun2.v17.lscache8.c.youtube.com/videoplayback?itag=34&signature=116A39056C180C76A80D8D0DBE4D1AF3463B4774.2E0CF144274ACFC7D9E7B9DFEB435E97DB445B35&burst=40&cp=U0hSSlhQUF9JTUNOMl9QSlRDOmxkYWtsN2g3dGhu&source=youtube&algorithm=throttle-factor&fexp=914076&range=13-1781759&expire=1335852243&cm2=1&ipbits=8&ip=197.0.0.0&upn=_7mY3O0kUVA&sparams=algorithm%2Cburst%2Ccp%2Cfactor%2Cid%2Cip%2Cipbits%2Citag%2Csource%2Cupn%2Cexpire&factor=1.25&keepalive=yes&id=a462420468723bba&sver=3&key=yt1";
	public float Debit ;

	

	public NetFlow(String Url) throws IOException
	{
//		url = Url;
//		URL u=null;
//		// Spécifier le chemin exact vers le fichier
//		try { 
//			u = new URL(url);
//
//		} catch (MalformedURLException e) {
//			System.err.println("URL non comprise.");
//			System.exit(-1);
//		} catch (IOException e) {
//			System.out.println("erreur");
//			System.err.println(e);
//			System.exit(-1);}
//		// Ouvrir la connexion, début de la communication avec le serveur
//
//		URLConnection uc = u.openConnection();
//		// Récupérer la taille exacte en nombre d’octets du fichier désigné, et la stocker dans un int
//		int taille = uc.getContentLength();
//		//System.out.println("la taille du fichier est " + taille + " octets");
//		if (taille == -1) {
//			throw new IOException("Fichier non valide.");
//		}
//		// Créer un flux d’entrée pour le fichier
//		InputStream brut = uc.getInputStream();
//
//		// Mettre ce flux d’entrée en cache (pour un meilleur transfert, plus sûr et plus régulier).
//		InputStream entree = new BufferedInputStream(brut,taille);
//		// Créer une matrice (un tableau) de bytes pour stocker tous les octets du fichier
//		byte[] donnees = new byte[taille];
//
//		// Pour l’instant aucun octet n’a encore été lu
//		int octetsLus = 0;
//		// Octets de déplacement, et octets déjà lus. 
//		int deplacement = 0; float alreadyRead = 0;
//
//		// Boucle permettant de parcourir tous les octets du fichier à lire
//		long next = 0;
//		//System.out.println("En cours de telechargement...");
//		long before = System.currentTimeMillis();
//		while(deplacement < taille)
//		{
//			//System.out.println(donnees.length-deplacement);
//			// utilisation de la methode "read" de la classe InputStream
//			octetsLus = entree.read(donnees, deplacement, donnees.length-deplacement);
//			//System.out.println(octetsLus);
//			next = System.currentTimeMillis();
//			// Petit calcul: mise à jour du nombre total d’octets lus par ajout au nombre d’octets lus au cours des précédents passages au nombre d’octets lus pendant ce passage
//			alreadyRead = alreadyRead + octetsLus;
//
//			// -1 marque par convention la fin d’un fichier, double opérateur "égale": = =
//			if(octetsLus == -1) break;
//
//			// se cadrer à un endroit précis du fichier pour lire les octets suivants, c’est le déplacement
//			deplacement += octetsLus;
//		} 
//		// fermer le flux d’entrée.
//		entree.close();
//
//		double temps = (double)(next-before)/1000;
//		double taille1 = (double)taille/1024;
//		Debit = (float)(taille1/temps);

	}

	public float getDebit()
	{
		return Debit;
	}

}

