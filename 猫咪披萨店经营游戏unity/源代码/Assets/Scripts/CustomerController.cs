using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CustomerController : MonoBehaviour
{
  public Image customerImage;
  public void SetCustomerImage(Sprite sprite)
  {
    if (sprite == null)
    {
      Debug.LogWarning("SetCustomerImage called with null sprite");
      return;
    }
    if(customerImage == null)
    {
      Debug.LogError("CustomerController: customerImage reference is missing!");
      return;
    }
    customerImage.sprite = sprite;
    customerImage.enabled = true;
    Debug.Log("Customer image set successfully");
  }
  private void Awake()
  {
    Debug.Log("CustomerController Awake");
  }

    public RectTransform rectTransform;
    public Vector2 startPos = new Vector2(-450, -62);
    public Vector2 targetPos = new Vector2(-220, -62);
    public float speed = 500f;

    private bool moving = false;
    // Start is called before the first frame update
    private void Start()
    {
        Debug.Log("CustomerController Start");

        OrderSystem os = FindObjectOfType<OrderSystem>();
        if(os != null)
        {
            os.PrepareNextOrder();
        }

        rectTransform.anchoredPosition = startPos;
        Appear();
    }

    // Update is called once per frame
    private void Appear()
    {
        moving = true;
        GameManager.Instance.ChangeState(GameState.CustomerArrive);
    }

    private void Update()
    {
      Debug.Log("CustomerController Update");
      if(!moving)return ;
      rectTransform.anchoredPosition=Vector2.MoveTowards(rectTransform.anchoredPosition,targetPos,speed*Time.deltaTime);
      if(Vector2.Distance( rectTransform.anchoredPosition,targetPos)<0.1f)
      {
        moving=false;
        Debug.Log("Customer reached target position");
        GameManager.Instance.ChangeState(GameState.Ordering);
        
        OrderSystem os = FindObjectOfType<OrderSystem>();
        Debug.Log("Customer arrived, showing order");
        if(os!=null)
        {
            os.ShowOrder();
      }
      else
      {
        Debug.LogError("OrderSystem not found in scene");
      }
      }
    }

}
