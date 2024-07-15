public class BackgroundMusicService {
    MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }
    @Override
     public int onStartCommand(Intent into, int flags, int startId){
        Log.d("mylog", "StartPlaying")
        mediaPlayer = MediaPlayer.create(this, R.raw.imperfect);
        mediaPlayer.start();
        return super.onStartCommand(intent, flags, startId);
            }


}
