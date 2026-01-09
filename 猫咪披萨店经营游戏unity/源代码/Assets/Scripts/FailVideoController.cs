using UnityEngine;
using UnityEngine.Video;

public class FailVideoController : MonoBehaviour
{
    public VideoPlayer videoPlayer;
    public GameObject failVideoRoot; // RawImage 对象
    public System.Action onVideoEnd; // 视频结束回调

    void Awake()
    {
        videoPlayer.loopPointReached += OnVideoEnd;
        failVideoRoot.SetActive(false);
    }

    // 播放失败视频
    public void PlayFailVideo()
    {
        failVideoRoot.SetActive(true);
        videoPlayer.Stop();
        videoPlayer.Play();
    }

    // 视频播放完毕
    private void OnVideoEnd(VideoPlayer vp)
    {
        failVideoRoot.SetActive(false);
        onVideoEnd?.Invoke();
    }
}
