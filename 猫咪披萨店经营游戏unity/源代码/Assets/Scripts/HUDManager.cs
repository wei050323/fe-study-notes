using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using TMPro;
using Unity.VisualScripting;
using UnityEngine.SceneManagement;

public class HUDManager : MonoBehaviour
{
    public static HUDManager Instance;
    
    [Header("UI Elements")]
    public TMP_Text timeText;
    public TMP_Text revenueText;
    public Button menuButton; // 用作重启按钮
    public GameObject menuPanel;

    private float gameTime = 0f;

    private void Awake()
    {
        // 单例模式，确保跨场景存在
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
            // 在 Awake 中订阅，确保尽早建立连接
            SubscribeToGameData();
        }
        else
        {
            // 如果已存在实例，销毁新的（避免重复）
            Destroy(gameObject);
        }
    }

    private void OnEnable()
    {
        // 每次启用时重新订阅并同步显示（场景切换后可能重新启用）
        // 使用协程延迟一帧，确保 GameData.Instance 已经初始化
        StartCoroutine(DelayedSubscribeAndSync());
    }
    
    private System.Collections.IEnumerator DelayedSubscribeAndSync()
    {
        // 等待一帧，确保所有对象都已初始化
        yield return null;
        
        if (GameData.Instance != null)
        {
            SubscribeToGameData();
        }
        else
        {
            Debug.LogWarning("HUDManager.OnEnable: GameData.Instance is null, will retry in Start()");
        }
    }

    private void Start()
    {
        // 只在首次创建时初始化 UI（避免重复初始化）
        if (Instance == this)
        {
            if (menuPanel != null)
                menuPanel.SetActive(false);
            
            // menuButton 直接用作重启按钮
            if (menuButton != null)
            {
                menuButton.onClick.AddListener(RestartGame);
            }
            
            // 确保订阅已建立并同步显示
            SubscribeToGameData();
        }
    }

    private void SubscribeToGameData()
    {
        // 从 GameData 同步收入并订阅变化事件
        if (GameData.Instance != null)
        {
            // 先取消订阅（避免重复订阅）
            GameData.Instance.OnRevenueChanged -= UpdateRevenueDisplay;
            // 再订阅
            GameData.Instance.OnRevenueChanged += UpdateRevenueDisplay;
            // 立即同步显示
            UpdateRevenueDisplay(GameData.Instance.totalRevenue);
            Debug.Log($"HUDManager subscribed to GameData. Current revenue: ${GameData.Instance.totalRevenue}");
        }
        else
        {
            Debug.LogWarning("HUDManager: GameData.Instance is null!");
        }
    }

    private void OnDestroy()
    {
        // 取消订阅，避免内存泄漏
        if (GameData.Instance != null)
        {
            GameData.Instance.OnRevenueChanged -= UpdateRevenueDisplay;
        }
    }

    // Update is called once per frame
    private void Update()
    {
        UpdateTime();
    }
    
    private void UpdateTime()
    {
        gameTime += Time.deltaTime;
        if (timeText != null)
        {
            int minutes = Mathf.FloorToInt(gameTime / 60f);
            int seconds = Mathf.FloorToInt(gameTime % 60f);
            timeText.text = $"{minutes:D2}:{seconds:D2}";
        }
    }
    
    // 更新收入显示（由 GameData 的事件触发）
    private void UpdateRevenueDisplay(int revenue)
    {
        Debug.Log($"HUDManager.UpdateRevenueDisplay called with revenue: ${revenue}");
        if (revenueText != null)
        {
            revenueText.text = $"${revenue}";
            Debug.Log($"HUDManager: Revenue text updated to: {revenueText.text}");
        }
        else
        {
            Debug.LogWarning("HUDManager: revenueText is null! Cannot update display.");
        }
    }
    
    // 保留此方法以保持向后兼容，但实际调用 GameData
    public void AddRevenue(int amount)
    {
        if (GameData.Instance != null)
        {
            GameData.Instance.AddRevenue(amount);
        }
    }
    
    // 重启游戏（menuButton 点击时调用）
    public void RestartGame()
    {
        Debug.Log("Restarting game...");
        
        // 1. 重置 GameData
        if (GameData.Instance != null)
        {
            GameData.Instance.totalRevenue = 0;
            GameData.Instance.currentOrder = null;
        }
        
        // 2. 重置游戏状态
        if (GameManager.Instance != null)
        {
            GameManager.Instance.ChangeState(GameState.Idle);
        }
        
        // 3. 重置 HUDManager 的时间
        gameTime = 0f;
        if (timeText != null)
        {
            timeText.text = "00:00";
        }
        
        // 4. 关闭菜单面板
        if (menuPanel != null)
        {
            menuPanel.SetActive(false);
        }
        
        // 5. 重新加载初始场景（OrderScene）
        // 使用协程延迟一帧，确保所有清理操作完成
        StartCoroutine(RestartGameCoroutine());
    }
    
    private System.Collections.IEnumerator RestartGameCoroutine()
    {
        // 等待一帧，确保所有清理操作完成
        yield return null;
        
        // 重新加载初始场景
        SceneManager.LoadScene("OrderScene");
        
        Debug.Log("Game restarted!");
    }
}
