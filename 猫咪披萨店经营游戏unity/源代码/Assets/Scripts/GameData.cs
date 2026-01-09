using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GameData : MonoBehaviour
{
    public static GameData Instance;
    public Order currentOrder;
    
    // 使用属性确保每次修改都触发 UI 更新
    private int _totalRevenue = 0;
    public int totalRevenue
    {
        get { return _totalRevenue; }
        set
        {
            if (_totalRevenue != value)
            {
                _totalRevenue = value;
                int subscriberCount = GetSubscriberCount();
                Debug.Log($"Revenue updated: ${_totalRevenue}, Subscribers: {subscriberCount}");
                OnRevenueChanged?.Invoke(_totalRevenue);
            }
        }
    }

    // 收入变化事件，用于通知 UI 更新
    public System.Action<int> OnRevenueChanged;
    
    // 获取当前订阅者数量（用于调试）
    public int GetSubscriberCount()
    {
        return OnRevenueChanged?.GetInvocationList().Length ?? 0;
    }

    private void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
        }
    }

    // 统一的收入添加方法
    public void AddRevenue(int amount)
    {
        totalRevenue += amount; // 通过属性设置，自动触发事件
    }
}
