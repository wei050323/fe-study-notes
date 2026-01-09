using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Video;

public class BakingVideoController : MonoBehaviour
{
    public VideoPlayer videoPlayer;
    public GameObject bakingVideoRoot; // RawImage 对象
    public System.Action onVideoEnd; // 视频结束回调
    void Awake()
    {
        videoPlayer.loopPointReached += OnVideoEnd;
        bakingVideoRoot.SetActive(false);
    }
    public void PlayBakingVideo()
    {
        bakingVideoRoot.SetActive(true);
        videoPlayer.Stop();
        videoPlayer.Play();
    }
    private void OnVideoEnd(VideoPlayer vp)
    {
        onVideoEnd?.Invoke();
    }
}
