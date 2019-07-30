package com.eryanet.m85music;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.eryanet.m85musicsdk.bean.CategoryBean;
import com.eryanet.m85musicsdk.bean.FavorBean;
import com.eryanet.m85musicsdk.bean.FavorResult;
import com.eryanet.m85musicsdk.bean.RecommendBean;
import com.eryanet.m85musicsdk.bean.Track;
import com.eryanet.m85musicsdk.bean.TrackBean;
import com.eryanet.m85musicsdk.inter.MusicListCallBack;
import com.eryanet.m85musicsdk.inter.MusicPlayerCallBack;
import com.eryanet.m85musicsdk.sdk.M85MusicSDK;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MusicListCallBack, MusicPlayerCallBack {
    @BindView(R.id.btn_recommend)
    Button btnRecommend;
    @BindView(R.id.btn_category)
    Button btnCategory;
    @BindView(R.id.btn_track_tag)
    Button btnTrackTag;
    @BindView(R.id.btn_track_album)
    Button btnTrackAlbum;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.btn_favor)
    Button btnFavor;
    @BindView(R.id.btn_play_list)
    Button btnPlayList;
    @BindView(R.id.btn_play)
    Button btnPlay;
    @BindView(R.id.btn_pause)
    Button btnPause;
    @BindView(R.id.btn_previous)
    Button btnPrevious;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.btn_mode)
    Button btnMode;
    @BindView(R.id.btn_progress)
    Button btnProgress;
    @BindView(R.id.btn_duration)
    Button btnDuration;
    @BindView(R.id.tv_song)
    TextView tvSong;
    @BindView(R.id.tv_singer)
    TextView tvSinger;
    @BindView(R.id.tv_seekbar)
    SeekBar tvSeekbar;
    private M85MusicSDK m85MusicSDK;
    private List<Track> trackList;
    private static final int SHOW_PROGRESS = 0x01;
    private static final int SHOW_PLAYMODE = 0x02;
    private static final int SHOW_TRACKINFO = 0x03;
    private int playMode = 0;
    private String songName;
    private String singerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        m85MusicSDK = M85MusicSDK.getInstance(this);
        m85MusicSDK.setMusicListCallBack(this);
        m85MusicSDK.setMusicPlayerCallBack(this);
        m85MusicSDK.initMusicPlayer();


    }

    public String formatTime(int time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        String getTime = simpleDateFormat.format(time);
        return getTime;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        m85MusicSDK.releaseMusicPlayer();
    }

    @Override
    public void responseRecommendList(RecommendBean recommendBean) {

    }

    @Override
    public void responseCategoryList(CategoryBean categoryBean) {

    }

    @Override
    public void responseTrackList(TrackBean trackBean) {
        trackList = trackBean.getTrackList();
        if (trackList != null && trackList.size() > 0) {
            /*for(int i =0;i<trackList.size();i++) {
                Log.e("zhangzimo",i+">>>"+trackList.get(i).getTitle()
                        +" "+trackList.get(i).isCollected()
                        +"  "+trackList.get(i).getFavor_id());
            }*/
        }


    }

    @Override
    public void responseFavorList(FavorBean favorBean) {

    }

    @Override
    public void responseFavorResult(FavorResult favorResult) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onPlayFail() {

    }

    @Override
    public void onTrackInfo(Track track) {
        songName = track.getTitle();
        singerName = track.getAnnouncer().getNickname();
        mHandler.sendEmptyMessage(SHOW_TRACKINFO);

    }

    @Override
    public void onPlayOrPause(int state) {

    }

    @Override
    public void onPlayMode(int mode) {
        Message message = new Message();
        playMode = mode;
        message.what = SHOW_PLAYMODE;
        message.arg1 = mode;
        mHandler.sendMessage(message);

    }

    @Override
    public void onProgress(long duration, long progress) {
        Message message = new Message();
        message.what = SHOW_PROGRESS;
        message.arg1 = (int) duration;
        message.arg2 = (int) progress;
        mHandler.sendMessage(message);


    }

    @Override
    public void onPlaySuccess() {

    }

    @OnClick({R.id.btn_recommend, R.id.btn_category, R.id.btn_track_tag, R.id.btn_track_album, R.id.btn_search,
            R.id.btn_add, R.id.btn_delete, R.id.btn_favor, R.id.btn_play_list, R.id.btn_play, R.id.btn_pause,
            R.id.btn_previous, R.id.btn_next, R.id.btn_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_recommend:
                m85MusicSDK.requestRecommendList();
                break;
            case R.id.btn_category:
                m85MusicSDK.requestCategoryList();
                break;
            case R.id.btn_track_tag:
                m85MusicSDK.requestTagDetail("1", 1, 20);
                break;
            case R.id.btn_track_album:
                m85MusicSDK.requestTrackList(5203142, 1, 20);
                break;
            case R.id.btn_search:
                m85MusicSDK.requestSearchTrackList("周杰伦", 1, 20);
                break;
            case R.id.btn_add:
                for (int i = 0; i < 3; i++) {
                    m85MusicSDK.addFavor(trackList.get(i));
                }
                break;
            case R.id.btn_delete:
                m85MusicSDK.deleteFavor(1611);
                break;
            case R.id.btn_favor:
                m85MusicSDK.requestFavorList();
                break;
            case R.id.btn_play_list:
                m85MusicSDK.playList(trackList, 0);
                break;
            case R.id.btn_play:
                m85MusicSDK.play();
                break;
            case R.id.btn_pause:
                m85MusicSDK.pause();
                break;
            case R.id.btn_previous:
                m85MusicSDK.playPre();
                break;
            case R.id.btn_next:
                m85MusicSDK.playNext();
                break;
            case R.id.btn_mode:
                if (playMode == 0) {
                    m85MusicSDK.setPlayMode(1);
                } else if (playMode == 1) {
                    m85MusicSDK.setPlayMode(2);
                } else if (playMode == 2) {
                    m85MusicSDK.setPlayMode(0);
                }

                break;
        }
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case SHOW_PROGRESS:
                    btnDuration.setText(formatTime(message.arg1));
                    btnProgress.setText(formatTime(message.arg2));
                    tvSeekbar.setMax(message.arg1);
                    tvSeekbar.setProgress(message.arg2);
                    break;
                case SHOW_PLAYMODE:
                    playMode = message.arg1;
                    if (playMode == 0) {
                        btnMode.setText("顺序播放");
                    } else if (playMode == 1) {
                        btnMode.setText("随机播放");
                    } else if (playMode == 2) {
                        btnMode.setText("单曲循环");
                    }
                    break;
                case SHOW_TRACKINFO:
                    tvSong.setText(songName);
                    tvSinger.setText(singerName);
                    break;
            }
            return false;
        }
    });
}
